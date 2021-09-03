package com.example.tyfserver.payment.controller;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.dto.VerifiedRefunder;
import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.PaymentPendingRequestException;
import com.example.tyfserver.payment.exception.RefundVerificationException;
import com.example.tyfserver.payment.exception.RefundVerificationReadyException;
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

    //todo: /payments 에서 /charge/ready 쪽으로 옮겨가야됨. PaymentController 네이밍이 ChargeController로 바뀔지도?
    @PostMapping
    public ResponseEntity<PaymentPendingResponse> payment(@Valid @RequestBody PaymentPendingRequest paymentPendingRequest, BindingResult result,
                                                          LoginMember loginMember) {
        if (result.hasErrors()) {
            throw new PaymentPendingRequestException();
        }

        PaymentPendingResponse response = paymentService.createPayment(paymentPendingRequest.getItemId(), loginMember);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund/verification/ready")
    public ResponseEntity<RefundVerificationReadyResponse> refundVerificationReady(@Valid @RequestBody RefundVerificationReadyRequest verificationReadyRequest, BindingResult result,
                                                                                   LoginMember loginMember) {
        if (result.hasErrors()) {
            throw new RefundVerificationReadyException();
        }

        RefundVerificationReadyResponse response = paymentService.refundVerificationReady(verificationReadyRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund/verification")
    public ResponseEntity<RefundVerificationResponse> refundVerification(@Valid @RequestBody RefundVerificationRequest verificationRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new RefundVerificationException();
        }

        RefundVerificationResponse response = paymentService.refundVerification(verificationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refund/info")
    public ResponseEntity<RefundInfoResponse> refundInfo(VerifiedRefunder refundInfoRequest) {
        RefundInfoResponse response = paymentService.refundInfo(refundInfoRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund")
    public ResponseEntity<Void> refundPayment(VerifiedRefunder verifiedRefunder) {
        paymentService.refundPayment(verifiedRefunder);
        return ResponseEntity.ok().build();
    }
}
