package com.example.tyfserver.payment.controller;

import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.PaymentCancelRequestException;
import com.example.tyfserver.payment.exception.PaymentPendingRequestException;
import com.example.tyfserver.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentPendingResponse> payment(@Valid @RequestBody PaymentPendingRequest paymentPendingRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new PaymentPendingRequestException();
        }

        PaymentPendingResponse response = paymentService.createPayment(paymentPendingRequest);
        return ResponseEntity.ok(response);
    }

    // todo 인증번호 발급 API
    // 인증번호 생성
    // 이메일로 발송
    @PostMapping("/refund/verification/ready")
    public ResponseEntity<RefundVerificationReadyResponse> refundVerificationReady(@Valid @RequestBody RefundVerificationReadyRequest verificationReadyRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException();
        }

        RefundVerificationReadyResponse response = paymentService.refundVerificationReady(verificationReadyRequest);
        return ResponseEntity.ok(response);
    }

    // todo 인증번호 확인 API
    // 인증번호 받고
    // 인증번호 확인 후
    // 액세스 토큰 응답
    @PostMapping("/refund/verification")
    public ResponseEntity<RefundVerificationResponse> refundVerification(@Valid @RequestBody RefundVerificationRequest verificationRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException();
        }

        RefundVerificationResponse response = paymentService.refundVerification(verificationRequest);
        return ResponseEntity.ok(response);
    }

    // todo 결제 정보 API: 액세스 토큰 필요
    // 액세스 토큰 받고
    // 그안에서 merchantUid 추출해서 결제정보 찾고
    // 결제정보 응답 (창작자,결제금액,결제일자 등등... Payment 정보)
//    @PostMapping("/refund/info")
//    public ResponseEntity<> refundInfo() {
//        paymentService.refundInfo();
//    }

    // todo 환불 API: 액세스 토큰 필요
    @PostMapping("/refund")
    public ResponseEntity<PaymentCancelResponse> cancelPayment(@Valid @RequestBody PaymentCancelRequest paymentCancelRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new PaymentCancelRequestException();
        }

        PaymentCancelResponse response = paymentService.cancelPayment(paymentCancelRequest);
        return ResponseEntity.ok(response);
    }
}
