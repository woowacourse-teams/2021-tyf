package com.example.tyfserver.payment.domain;

public interface PaymentServiceConnector {

    PaymentInfo requestPaymentInfo(Long merchantUid);

    PaymentInfo requestPaymentCancel(Long merchantUid);
}
