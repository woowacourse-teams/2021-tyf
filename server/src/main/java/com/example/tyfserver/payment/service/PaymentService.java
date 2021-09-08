package com.example.tyfserver.payment.service;

import com.example.tyfserver.auth.domain.CodeResendCoolTime;
import com.example.tyfserver.auth.domain.VerificationCode;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.dto.VerifiedRefunder;
import com.example.tyfserver.auth.exception.VerificationCodeNotFoundException;
import com.example.tyfserver.auth.exception.VerificationFailedException;
import com.example.tyfserver.auth.repository.CodeResendCoolTimeRepository;
import com.example.tyfserver.auth.repository.VerificationCodeRepository;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.*;
import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.*;
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
    private final RefundFailureRepository refundFailureRepository;

    private final PaymentServiceConnector paymentServiceConnector;
    private final SmtpMailConnector smtpMailConnector;

    private final VerificationCodeRepository verificationCodeRepository;
    private final CodeResendCoolTimeRepository codeResendCoolTimeRepository;
    private final AuthenticationService authenticationService;

    public PaymentPendingResponse createPayment(String itemId, LoginMember loginMember) {
        Member donator = memberRepository
                .findById(loginMember.getId())
                .orElseThrow(MemberNotFoundException::new);

        Item item = Item.findItem(itemId);
        Payment payment = new Payment(item.getItemPrice(), item.getItemName());
        donator.addPayment(payment);
        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentPendingResponse(savedPayment);
    }

    public PaymentCompleteResponse completePayment(PaymentCompleteRequest paymentCompleteRequest) {
        UUID merchantUid = UUID.fromString(paymentCompleteRequest.getMerchantUid());
        PaymentInfo paymentInfo = paymentServiceConnector.requestPaymentInfo(merchantUid);

        Payment payment = findPayment(merchantUid);
        payment.complete(paymentInfo);
        smtpMailConnector.sendChargeComplete(payment);
        return new PaymentCompleteResponse(payment.getAmount());
    }

    public RefundVerificationReadyResponse refundVerificationReady(RefundVerificationReadyRequest refundVerificationReadyRequest) {
        String merchantUid = refundVerificationReadyRequest.getMerchantUid();
        Payment payment = findPayment(merchantUid);

        validateCanRefund(payment);
        Integer resendCoolTime = checkResendCoolTime(merchantUid);
        VerificationCode verificationCode = verificationCodeRepository
                .save(VerificationCode.newCode(merchantUid));

        smtpMailConnector.sendVerificationCode(payment.getEmail(), verificationCode.getCode());

        return new RefundVerificationReadyResponse(
                payment.getMaskedEmail(),
                verificationCode.getTimeout(),
                resendCoolTime
        );
    }

    private void validateCanRefund(Payment payment) {
        if (payment.isRefundBlocked()) {
            throw new RefundVerificationBlockedException();
        }
        if (payment.isNotPaid()) {
            throw new CannotRefundException(payment.getStatus());
        }
        if (payment.isAfterRefundGuaranteeDuration()) {
            throw new RefundGuaranteeDurationException();
        }
        payment.validateMemberHasRefundablePoint();
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

        return new RefundInfoResponse(payment);
    }

    public void refundPayment(VerifiedRefunder verifiedRefunder) {
        Payment payment = findPayment(verifiedRefunder.getMerchantUid());
        payment.validateIsNotCancelled();
        PaymentInfo refundInfo = paymentServiceConnector.requestPaymentRefund(payment.getMerchantUid());
        payment.refund(refundInfo);
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
