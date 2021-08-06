package com.example.tyfserver.payment.service;

import com.example.tyfserver.auth.domain.CodeResendCoolTime;
import com.example.tyfserver.auth.domain.VerificationCode;
import com.example.tyfserver.auth.dto.VerifiedRefundRequest;
import com.example.tyfserver.auth.exception.VerificationCodeNotFoundException;
import com.example.tyfserver.auth.exception.VerificationFailedException;
import com.example.tyfserver.auth.repository.CodeResendCoolTimeRepository;
import com.example.tyfserver.auth.repository.VerificationCodeRepository;
import com.example.tyfserver.auth.service.AuthenticationService;
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
import com.example.tyfserver.payment.exception.PaymentNotFoundException;
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
        Integer resendCoolTime = checkResendCoolTime(merchantUid);

        VerificationCode verificationCode = VerificationCode.newCode(merchantUid);
        verificationCodeRepository.save(verificationCode);
        Payment payment = findPayment(verificationCode.getMerchantUid());

        // todo 이메일로 전송

        return new RefundVerificationReadyResponse(
                payment.getMaskedEmail(),
                verificationCode.getTimeout(),
                resendCoolTime
        );
    }

    private Integer checkResendCoolTime(String merchantUid) {
        codeResendCoolTimeRepository.findById(merchantUid)
                .ifPresent(codeResendCoolTime -> {
                    // todo 인증번호 재발송 쿨타임이라면 400에러
                    throw new RuntimeException();
                });
        CodeResendCoolTime resendCoolTime = codeResendCoolTimeRepository.save(new CodeResendCoolTime(merchantUid));
        return resendCoolTime.getTimeout();
    }

    public RefundVerificationResponse refundVerification(RefundVerificationRequest verificationRequest) {
        String merChantUid = verificationRequest.getMerChantUid();
        Payment payment = paymentRepository.findByMerchantUid(UUID.fromString(merChantUid))
                .orElseThrow(RuntimeException::new);

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
        payment.checkRemainTryCount(); // todo: (해당 메서드를 중복으로 사용하는게 좋을지) 아니면 (인증 요청을 하면서 tryCount가 1 -> 0이 되는 순간 예외발생하게 할지?)
    }

    public RefundInfoResponse refundInfo(VerifiedRefundRequest refundInfoRequest) {
        Payment payment = findPayment(refundInfoRequest.getMerchantUid());
        Donation donation = donationRepository.findByPaymentId(payment.getId())
                .orElseThrow(DonationNotFoundException::new);
        Member member = memberRepository.findByPageName(payment.getPageName())
                .orElseThrow(MemberNotFoundException::new);
        return new RefundInfoResponse(payment, donation, member);
    }

    public PaymentCancelResponse refundPayment(VerifiedRefundRequest verifiedRefundRequest) {
        Payment payment = findPayment(verifiedRefundRequest.getMerchantUid());

        PaymentInfo paymentCancelInfo = paymentServiceConnector.requestPaymentCancel(payment.getMerchantUid());

        payment.refund(paymentCancelInfo);
        // todo Member의 포인트에서 차감, 도네이션 취소 등의 로직 필요.
        return new PaymentCancelResponse(payment.getMerchantUid());
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
