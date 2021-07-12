package com.example.tyfserver.donation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationRequest {

    private Long creatorId;
    private Long donationAmount;

    public DonationRequest(Long creatorId, Long donationAmount) {
        this.creatorId = creatorId;
        this.donationAmount = donationAmount;
    }
}
