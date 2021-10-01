package com.example.tyfserver.donation.domain;

import lombok.Getter;

@Getter
public enum DonationStatus {
    // todo Donation 취소 기능 구현할지 결정해야함. 구현안한다면 CANCELLED 제거
    REFUNDABLE("환불가능"), EXCHANGEABLE("정산가능"), EXCHANGED("정산완료"), CANCELLED("취소됨"),
    WAITING_FOR_EXCHANGE("정산 대기중");

    private final String information;

    DonationStatus(String information) {
        this.information = information;
    }
}
