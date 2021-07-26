package com.example.tyfserver.payment.domain;

import com.example.tyfserver.payment.dto.PaymentRequest;

public interface PaymentServiceConnector {
    PaymentInfo requestPaymentInfo(PaymentRequest paymentRequest);
}
