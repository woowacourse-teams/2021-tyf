package com.example.tyfserver.payment.controller;

import com.example.tyfserver.auth.dto.VerifiedRefundRequest;
import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.PaymentPendingRequestException;
import com.example.tyfserver.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/refund/verification/ready")
    public ResponseEntity<RefundVerificationReadyResponse> refundVerificationReady(@Valid @RequestBody RefundVerificationReadyRequest verificationReadyRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException();
        }

        RefundVerificationReadyResponse response = paymentService.refundVerificationReady(verificationReadyRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund/verification")
    public ResponseEntity<RefundVerificationResponse> refundVerification(@Valid @RequestBody RefundVerificationRequest verificationRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException();
        }

        RefundVerificationResponse response = paymentService.refundVerification(verificationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refund/info")
    public ResponseEntity<RefundInfoResponse> refundInfo(VerifiedRefundRequest refundInfoRequest) {
        RefundInfoResponse response = paymentService.refundInfo(refundInfoRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund")
    // todo PaymentRefundResponse 리턴할 필요있나? 200으로 충분할 듯?
    public ResponseEntity<PaymentRefundResponse> refundPayment(VerifiedRefundRequest verifiedRefundRequest) {
        PaymentRefundResponse response = paymentService.refundPayment(verifiedRefundRequest);
        return ResponseEntity.ok(response);
    }
}
