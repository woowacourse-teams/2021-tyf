package com.example.tyfserver.payment.domain;

import java.util.UUID;

public interface PaymentServiceConnector {

    PaymentInfo requestPaymentInfo(UUID merchantUid);

    PaymentInfo requestPaymentRefund(UUID merchantUid);

    String requestHolderNameOfAccount(String bankCode, String bankNum);
}
