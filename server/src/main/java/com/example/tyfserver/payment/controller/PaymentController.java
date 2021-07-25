package com.example.tyfserver.payment.controller;

import com.example.tyfserver.payment.dto.PaymentRequest;
import com.example.tyfserver.payment.dto.PaymentResponse;
import com.example.tyfserver.payment.dto.PaymentSaveRequest;
import com.example.tyfserver.payment.dto.PaymentSaveResponse;
import com.example.tyfserver.payment.exception.PaymentRequestException;
import com.example.tyfserver.payment.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentsService paymentsService;

    @PostMapping
    public ResponseEntity<PaymentSaveResponse> payment(@RequestBody PaymentSaveRequest paymentSaveRequest) {
        // todo: merchantUid 는 무조건 전달해줘야한다!
        PaymentSaveResponse response = paymentsService.createPayment(paymentSaveRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/complete")
    public ResponseEntity<PaymentResponse> paymentComplete(@RequestBody PaymentRequest paymentRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new PaymentRequestException();
        }

        return ResponseEntity.ok(paymentsService.completePayment(paymentRequest));
    }
}
