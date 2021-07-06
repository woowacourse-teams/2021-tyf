package com.example.tyfserver.donation.dto;

import lombok.Getter;

@Getter
public class DonationRequest {

    private final Long creatorId;
    private final Long donationAmount;

    public DonationRequest(final Long creatorId, final Long donationAmount) {
        this.creatorId = creatorId;
        this.donationAmount = donationAmount;
    }
}
