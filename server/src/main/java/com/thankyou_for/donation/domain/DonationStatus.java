package com.thankyou_for.donation.domain;

import lombok.Getter;

@Getter
public enum DonationStatus {
    WAITING_FOR_EXCHANGE("정산대기"), EXCHANGED("정산완료");

    private final String information;

    DonationStatus(String information) {
        this.information = information;
    }
}
