package com.example.tyfserver.payment.controller;

import com.example.tyfserver.payment.dto.PaymentSaveRequest;
import com.example.tyfserver.payment.dto.PaymentSaveResponse;
import com.example.tyfserver.payment.exception.PaymentRequestException;
import com.example.tyfserver.payment.exception.PaymentSaveRequestException;
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
    public ResponseEntity<PaymentSaveResponse> payment(@Valid @RequestBody PaymentSaveRequest paymentSaveRequest, BindingResult result) {

        if (result.hasErrors()) {
            throw new PaymentSaveRequestException();
        }

        PaymentSaveResponse response = paymentService.createPayment(paymentSaveRequest);
        return ResponseEntity.ok(response);
    }
}
