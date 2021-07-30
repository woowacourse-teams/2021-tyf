package com.example.tyfserver.payment.service;

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

    public PaymentPendingResponse createPayment(PaymentPendingRequest pendingRequest) {
        Member creator = memberRepository
                .findByPageName(pendingRequest.getPageName())
                .orElseThrow(MemberNotFoundException::new);

        Payment payment = new Payment(pendingRequest.getAmount(), pendingRequest.getEmail(), creator.getPageName());
        return new PaymentPendingResponse(paymentRepository.save(payment));
    }

    public Payment completePayment(PaymentCompleteRequest paymentCompleteRequest) {
        PaymentInfo paymentInfo = paymentServiceConnector
                .requestPaymentInfo(paymentCompleteRequest.getMerchantUid());

        Payment payment = findPayment(paymentCompleteRequest.getMerchantUid());

        payment.complete(paymentInfo);
        return payment;
    }

    public PaymentCancelResponse cancelPayment(PaymentCancelRequest paymentCancelRequest) {
        Payment payment = findPayment(UUID.fromString(paymentCancelRequest.getMerchantUid()));

        PaymentInfo paymentCancelInfo = paymentServiceConnector.requestPaymentCancel(payment.getMerchantUid());

        payment.cancel(paymentCancelInfo);
        // todo Member의 포인트에서 차감, 도네이션 취소 등의 로직 필요.
        return new PaymentCancelResponse(payment.getMerchantUid());
    }

    private Payment findPayment(UUID uuid) {
        return paymentRepository
                .findByMerchantUid(uuid)
                .orElseThrow(PaymentNotFoundException::new);
    }
}
