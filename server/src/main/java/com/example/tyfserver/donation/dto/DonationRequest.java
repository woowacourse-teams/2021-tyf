package com.example.tyfserver.donation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationRequest {

    @NotBlank
    private Long creatorId;
    @Positive
    private Long donationAmount;

    public DonationRequest(Long creatorId, Long donationAmount) {
        this.creatorId = creatorId;
        this.donationAmount = donationAmount;
    }
}
