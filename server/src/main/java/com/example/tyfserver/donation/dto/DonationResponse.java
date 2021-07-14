package com.example.tyfserver.donation.dto;

import com.example.tyfserver.donation.domain.Donation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationResponse {

    private Long donationId;
    private String name;
    private String message;
    private Long amount;

    public DonationResponse(Long donationId, String name, String message, Long amount) {
        this.donationId = donationId;
        this.name = name;
        this.message = message;
        this.amount = amount;
    }

    public DonationResponse(Donation donation) {
        this(donation.getId(), donation.getName(), donation.getMessage(), donation.getAmount());
    }
}
