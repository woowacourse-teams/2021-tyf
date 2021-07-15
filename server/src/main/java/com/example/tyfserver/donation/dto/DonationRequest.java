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
    private String pageName;
    @Positive
    private Long donationAmount;

    public DonationRequest(String pageName, Long donationAmount) {
        this.pageName = pageName;
        this.donationAmount = donationAmount;
    }
}
