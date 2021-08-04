package com.example.tyfserver.payment.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentInfo {
    private UUID merchantUid;
    private PaymentStatus status;
    private Long amount;
    private String pageName;
    private String impUid;
    private String module;

    public PaymentInfo(UUID merchantUid, PaymentStatus status, Long amount, String pageName, String impUid, String module) {
        this.merchantUid = merchantUid;
        this.status = status;
        this.amount = amount;
        this.pageName = pageName;
        this.impUid = impUid;
        this.module = module;
    }
}
