package com.example.tyfserver.payment.domain;

import com.example.tyfserver.payment.util.IamPortPaymentServiceConnector;
import lombok.Getter;

@Getter
public class PaymentInfo {
    private Long merchantId;
    private PaymentStatus status;
    private Long amount;
    private String pageName;
    private String impUid;
    private String module;

    public PaymentInfo(Long merchantId, PaymentStatus status, Long amount, String pageName, String impUid, String module) {
        this.merchantId = merchantId;
        this.status = status;
        this.amount = amount;
        this.pageName = pageName;
        this.impUid = impUid;
        this.module = module;
    }
}
