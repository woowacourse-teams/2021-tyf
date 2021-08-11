package com.example.tyfserver.payment.domain;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    READY("미결제"), PENDING("결제대기중"), PAID("결제완료"), CANCELLED("결제취소"), FAILED("결제실패"),
    INVALID("위변조검증실패");

    private final String information;

    PaymentStatus(String info) {
        this.information = info;
    }

    public boolean isPaid() {
        return this == PAID;
    }

    public boolean isCancelled() {
        return this == CANCELLED;
    }
}
