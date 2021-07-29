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

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final PaymentServiceConnector paymentServiceConnector;

    public PaymentPendingResponse createPayment(PaymentPendingRequest saveRequest) {
        Member creator = memberRepository
                .findByPageName(saveRequest.getPageName())
                .orElseThrow(MemberNotFoundException::new);

        Payment payment = new Payment(saveRequest.getAmount(), saveRequest.getEmail(), creator.getPageName());
        return new PaymentPendingResponse(paymentRepository.save(payment));
        // todo: PaymentSaveResponse에 어떤 필드값들이 있으면 좋을까? (일단은 정말 필요한 값인 merchantUid만 추가!)
    }

    public Payment completePayment(PaymentRequest paymentRequest) {
        PaymentInfo paymentInfo = paymentServiceConnector
                .requestPaymentInfo(paymentRequest.getMerchantUid());

        Payment payment = paymentRepository
                .findById(paymentRequest.getMerchantUid())
                .orElseThrow(PaymentNotFoundException::new);

        payment.complete(paymentInfo);
        return payment;
    }

    public PaymentCancelResponse cancelPayment(PaymentCancelRequest paymentCancelRequest) {
        Payment payment = paymentRepository
                .findById(paymentCancelRequest.getMerchantUid())
                .orElseThrow(RuntimeException::new);

        PaymentInfo paymentCancelInfo = paymentServiceConnector.requestPaymentCancel(payment.getId());

        payment.cancel(paymentCancelInfo);
        return new PaymentCancelResponse(payment.getId());
    }
}
