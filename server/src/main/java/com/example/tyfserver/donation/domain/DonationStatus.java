package com.example.tyfserver.donation.domain;

public enum DonationStatus {
    REFUNDABLE, EXCHANGEABLE, EXCHANGED, CANCELLED;

    public boolean isRefundable() {
        return this == REFUNDABLE;
    }
}
