package com.example.tyfserver.payment.domain;

import lombok.Getter;

@Getter
public class PaymentInfo {
    private Long id;
    private PaymentStatus status;
    private Long amount;
    private String pageName;
    private String serviceId;

    public PaymentInfo(Long id, PaymentStatus status, Long amount, String pageName, String serviceId) {
        this.id = id;
        this.status = status;
        this.amount = amount;
        this.pageName = pageName;
        this.serviceId = serviceId;
    }
}
