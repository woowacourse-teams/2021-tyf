package com.example.tyfserver.donation.domain;

public enum DonationStatus {

    REFUNDABLE, EXCHANGEABLE, CANCELLED; //todo: VALID --> 2개,  (7일 이전 정산 불가, 환불가능 / 7일 이후 정산 가능, 환불 불가능)    환불된게 CANCELLED
}
