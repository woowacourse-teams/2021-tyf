package com.example.tyfserver.payment.domain;

import lombok.Getter;

@Getter
public class PaymentInfo {
    private Long merchantId;
    private PaymentStatus status;
    private Long amount;
    private String pageName;
    private String impUid;

    public PaymentInfo(Long merchantId, PaymentStatus status, Long amount, String pageName, String impUid) {
        this.merchantId = merchantId;
        this.status = status;
        this.amount = amount;
        this.pageName = pageName;
        this.impUid = impUid;
    }
}
