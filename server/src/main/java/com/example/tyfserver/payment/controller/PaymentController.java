package com.example.tyfserver.payment.controller;

import com.example.tyfserver.payment.dto.PaymentCancelRequest;
import com.example.tyfserver.payment.dto.PaymentCancelResponse;
import com.example.tyfserver.payment.dto.PaymentPendingRequest;
import com.example.tyfserver.payment.dto.PaymentPendingResponse;
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

    @PostMapping("/cancel")
    public ResponseEntity<PaymentCancelResponse> cancelPayment(@Valid @RequestBody PaymentCancelRequest paymentCancelRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new PaymentPendingRequestException();
        }

        PaymentCancelResponse response = paymentService.cancelPayment(paymentCancelRequest);
        return ResponseEntity.ok(response);
    }
}
