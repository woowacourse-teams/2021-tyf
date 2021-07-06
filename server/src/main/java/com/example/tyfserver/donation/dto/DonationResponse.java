package com.example.tyfserver.donation.dto;

import com.example.tyfserver.member.domain.Member;
import lombok.Getter;

@Getter
public class DonationResponse {

    private final Long donationId;

    public DonationResponse(final Long donationId) {
        this.donationId = donationId;
    }

    public static DonationResponse from(final Member member) {
        return new DonationResponse(member.getId());
    }
}
