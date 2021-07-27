package com.example.tyfserver.payment.controller;

import com.example.tyfserver.payment.dto.PaymentSaveRequest;
import com.example.tyfserver.payment.dto.PaymentSaveResponse;
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
    public ResponseEntity<PaymentSaveResponse> payment(@RequestBody PaymentSaveRequest paymentSaveRequest) {
        PaymentSaveResponse response = paymentService.createPayment(paymentSaveRequest);
        return ResponseEntity.ok(response);
    }
}
