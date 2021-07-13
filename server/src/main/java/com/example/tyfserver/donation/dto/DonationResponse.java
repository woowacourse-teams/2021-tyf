package com.example.tyfserver.donation.dto;

import com.example.tyfserver.member.domain.Member;
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

    public static DonationResponse from(final Member member) {
        return new DonationResponse(member.getId());
    }
}
