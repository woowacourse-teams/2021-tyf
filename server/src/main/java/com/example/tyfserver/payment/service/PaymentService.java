package com.example.tyfserver.payment.service;

import com.example.tyfserver.auth.domain.VerificationCode;
import com.example.tyfserver.auth.dto.VerifiedRefundRequest;
import com.example.tyfserver.auth.repository.VerificationCodeRepository;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.domain.PaymentInfo;
import com.example.tyfserver.payment.domain.PaymentServiceConnector;
import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.PaymentNotFoundException;
import com.example.tyfserver.payment.repository.PaymentRepository;
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
    private final PaymentServiceConnector paymentServiceConnector;
    private final VerificationCodeRepository verificationCodeRepository;
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
        VerificationCode verificationCode = VerificationCode.newCode(refundVerificationReadyRequest.getMerchantUid());
        verificationCodeRepository.save(verificationCode);

        Payment payment = findPayment(verificationCode.getMerchantUid());

        // todo 이메일로 전송
        // todo timeout, resendCoolTime 같이 응답
        return new RefundVerificationReadyResponse(payment.getMaskedEmail());
    }

    public RefundVerificationResponse refundVerification(RefundVerificationRequest verificationRequest) {
        // 인증번호 확인 후
        String merChantUid = verificationRequest.getMerChantUid();
        VerificationCode verificationCode = verificationCodeRepository.findById(merChantUid)
                .orElseThrow(RuntimeException::new);

        verificationCode.verify(verificationRequest.getVerificationCode());

        // 액세스 토큰 응답
        String refundToken = authenticationService.createRefundToken(merChantUid);

        return new RefundVerificationResponse(refundToken);
    }

    public RefundInfoResponse refundInfo(VerifiedRefundRequest refundInfoRequest) {
        Payment payment = findPayment(refundInfoRequest.getMerchantUid());
        return new RefundInfoResponse(payment);
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
