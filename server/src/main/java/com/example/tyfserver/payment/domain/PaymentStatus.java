package com.example.tyfserver.payment.domain;

public enum PaymentStatus {
    READY("미결제"), PAID("결제완료"), CANCELLED("결제취소"), FAILED("결제실패");

    private final String information;

    PaymentStatus(String info) {
        this.information = info;
    }
}
