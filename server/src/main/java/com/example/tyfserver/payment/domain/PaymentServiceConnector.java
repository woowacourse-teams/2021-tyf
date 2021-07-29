package com.example.tyfserver.payment.domain;

import java.util.UUID;

public interface PaymentServiceConnector {

    PaymentInfo requestPaymentInfo(Long merchantUid);

    PaymentInfo requestPaymentCancel(UUID merchantUid);
}
