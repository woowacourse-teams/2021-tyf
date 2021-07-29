package com.example.tyfserver.payment.controller;

import com.example.tyfserver.payment.dto.PaymentCancelRequest;
import com.example.tyfserver.payment.dto.PaymentCancelResponse;
import com.example.tyfserver.payment.dto.PaymentPendingRequest;
import com.example.tyfserver.payment.dto.PaymentPendingResponse;
import com.example.tyfserver.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentPendingResponse> payment(@RequestBody PaymentPendingRequest paymentPendingRequest) {
        PaymentPendingResponse response = paymentService.createPayment(paymentPendingRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<PaymentCancelResponse> cancelPayment(@RequestBody PaymentCancelRequest paymentCancelRequest) {
        PaymentCancelResponse response = paymentService.cancelPayment(paymentCancelRequest);
        return ResponseEntity.ok(response);
    }
}
