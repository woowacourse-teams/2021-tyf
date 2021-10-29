package com.example.tyfserver.payment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class PaymentInfo {
    private UUID merchantUid;
    private PaymentStatus status;
    private Long amount;
    private String itemName;
    private String impUid;
    private String module;

    public PaymentInfo(UUID merchantUid, PaymentStatus status, Long amount, String itemName, String impUid, String module) {
        this.merchantUid = merchantUid;
        this.status = status;
        this.amount = amount;
        this.itemName = itemName;
        this.impUid = impUid;
        this.module = module;
    }
}
