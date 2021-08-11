package com.example.tyfserver.payment.service;

import com.example.tyfserver.auth.domain.CodeResendCoolTime;
import com.example.tyfserver.auth.domain.VerificationCode;
import com.example.tyfserver.auth.dto.VerifiedRefunder;
import com.example.tyfserver.auth.exception.VerificationCodeNotFoundException;
import com.example.tyfserver.auth.exception.VerificationFailedException;
import com.example.tyfserver.auth.repository.CodeResendCoolTimeRepository;
import com.example.tyfserver.auth.repository.VerificationCodeRepository;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.exception.DonationNotFoundException;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.domain.PaymentInfo;
import com.example.tyfserver.payment.domain.PaymentServiceConnector;
import com.example.tyfserver.payment.domain.RefundFailure;
import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.CannotRefundException;
import com.example.tyfserver.payment.exception.CodeResendCoolTimeException;
import com.example.tyfserver.payment.exception.PaymentNotFoundException;
import com.example.tyfserver.payment.exception.RefundVerificationBlockedException;
import com.example.tyfserver.payment.repository.PaymentRepository;
import com.example.tyfserver.payment.repository.RefundFailureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final DonationRepository donationRepository;
    private final RefundFailureRepository refundFailureRepository;

    private final PaymentServiceConnector paymentServiceConnector;
    private final SmtpMailConnector smtpMailConnector;

    private final VerificationCodeRepository verificationCodeRepository;
    private final CodeResendCoolTimeRepository codeResendCoolTimeRepository;
    private final AuthenticationService authenticationService;

    public PaymentPendingResponse createPayment(PaymentPendingRequest pendingRequest) {
        Member creator = memberRepository
                .findByPageName(pendingRequest.getPageName())
                .orElseThrow(MemberNotFoundException::new);

        Payment payment = new Payment(pendingRequest.getAmount(), pendingRequest.getEmail(), creator.getPageName());
        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentPendingResponse(savedPayment);
    }

    public Payment completePayment(PaymentCompleteRequest paymentCompleteRequest) {
        UUID merchantUid = UUID.fromString(paymentCompleteRequest.getMerchantUid());
        PaymentInfo paymentInfo = paymentServiceConnector.requestPaymentInfo(merchantUid);

        Payment payment = findPayment(merchantUid);
        payment.complete(paymentInfo);
        return payment;
    }

    public RefundVerificationReadyResponse refundVerificationReady(RefundVerificationReadyRequest refundVerificationReadyRequest) {
        String merchantUid = refundVerificationReadyRequest.getMerchantUid();
        Payment payment = findPayment(merchantUid);
        Donation donation = donationRepository.findByPaymentId(payment.getId())
                .orElseThrow(DonationNotFoundException::new);

        validateStatus(payment, donation);
        Integer resendCoolTime = checkResendCoolTime(merchantUid);
        VerificationCode verificationCode = verificationCodeRepository
                .save(VerificationCode.newCode(merchantUid));

        smtpMailConnector.sendVerificationCode(payment.getEmail().getEmail(), verificationCode.getCode());

        return new RefundVerificationReadyResponse(
                payment.getMaskedEmail(),
                verificationCode.getTimeout(),
                resendCoolTime
        );
    }

    private void validateStatus(Payment payment, Donation donation) {
        if (!payment.getStatus().isPaid()) {
            throw new CannotRefundException(payment.getStatus());
        }
        if (!donation.getStatus().isRefundable()) {
            throw new CannotRefundException(donation.getStatus());
        }
    }

    private Integer checkResendCoolTime(String merchantUid) {
        codeResendCoolTimeRepository.findById(merchantUid)
                .ifPresent(codeResendCoolTime -> {
                    throw new CodeResendCoolTimeException();
                });
        CodeResendCoolTime resendCoolTime = codeResendCoolTimeRepository.save(new CodeResendCoolTime(merchantUid));
        return resendCoolTime.getTimeout();
    }

    @Transactional(noRollbackFor = {VerificationFailedException.class, RefundVerificationBlockedException.class})
    public RefundVerificationResponse refundVerification(RefundVerificationRequest verificationRequest) {
        String merChantUid = verificationRequest.getMerchantUid();
        Payment payment = paymentRepository.findByMerchantUidWithRefundFailure(UUID.fromString(merChantUid))
                .orElseThrow(PaymentNotFoundException::new);

        payment.checkRemainTryCount();
        verify(verificationRequest.getVerificationCode(), payment, merChantUid);

        String refundToken = authenticationService.createRefundToken(merChantUid);
        return new RefundVerificationResponse(refundToken);
    }

    private void verify(String code, Payment payment, String merChantUid) {
        VerificationCode verificationCode = verificationCodeRepository.findById(merChantUid)
                .orElseThrow(VerificationCodeNotFoundException::new);
        if (verificationCode.isUnverified(code)) {
            reduceRefundTryCount(payment);
            throw new VerificationFailedException(payment.getRemainTryCount());
        }
    }

    private void reduceRefundTryCount(Payment payment) {
        if (payment.hasNoRefundFailure()) {
            RefundFailure refundFailure = refundFailureRepository.save(new RefundFailure());
            payment.updateRefundFailure(refundFailure);
        }
        payment.reduceTryCount();
        payment.checkRemainTryCount();
    }

    public RefundInfoResponse refundInfo(VerifiedRefunder refundInfoRequest) {
        Payment payment = findPayment(refundInfoRequest.getMerchantUid());
        Donation donation = donationRepository.findByPaymentId(payment.getId())
                .orElseThrow(DonationNotFoundException::new);
        Member member = memberRepository.findByPageName(payment.getPageName())
                .orElseThrow(MemberNotFoundException::new);
        return new RefundInfoResponse(payment, donation, member);
    }

    public void refundPayment(VerifiedRefunder verifiedRefunder) {
        Payment payment = findPayment(verifiedRefunder.getMerchantUid());
        Donation donation = donationRepository.findByPaymentId(payment.getId())
                .orElseThrow(DonationNotFoundException::new);

        donation.validateIsNotCancelled();
        payment.validateIsNotCancelled();

        PaymentInfo refundInfo = paymentServiceConnector.requestPaymentRefund(payment.getMerchantUid());

        payment.refund(refundInfo);
        donation.cancel();
    }

    private Payment findPayment(String merchantUid) {
        return findPayment(UUID.fromString(merchantUid));
    }

    private Payment findPayment(UUID merchantUid) {
        return paymentRepository
                .findByMerchantUid(merchantUid)
                .orElseThrow(PaymentNotFoundException::new);
    }
}
