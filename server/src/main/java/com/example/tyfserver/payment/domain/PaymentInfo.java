package com.example.tyfserver.payment.domain;

import lombok.Getter;

@Getter
public class PaymentInfo {
    private Long merchantUid;
    private PaymentStatus status;
    private Long amount;
    private String pageName;
    private String impUid;

    public PaymentInfo(Long merchantUid, PaymentStatus status, Long amount, String pageName, String impUid) {
        this.merchantUid = merchantUid;
        this.status = status;
        this.amount = amount;
        this.pageName = pageName;
        this.impUid = impUid;
    }
}
