package com.example.tyfserver.donation.dto;

import com.example.tyfserver.donation.domain.Donation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationResponse {

    private Long donationId;

    public DonationResponse(final Long donationId) {
        this.donationId = donationId;
    }

    public static DonationResponse from(final Donation donation) {
        return new DonationResponse(donation.getId());
    }
}
