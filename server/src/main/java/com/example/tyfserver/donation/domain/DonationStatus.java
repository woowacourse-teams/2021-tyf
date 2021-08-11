package com.example.tyfserver.donation.domain;

import lombok.Getter;

@Getter
public enum DonationStatus {
    REFUNDABLE("환불가능"), EXCHANGEABLE("정산가능"), EXCHANGED("정산완료"), CANCELLED("취소됨");

    private final String information;

    DonationStatus(String information) {
        this.information = information;
    }

    public boolean isRefundable() {
        return this == REFUNDABLE;
    }
}
