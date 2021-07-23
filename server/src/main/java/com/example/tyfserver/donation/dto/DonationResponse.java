package com.example.tyfserver.donation.dto;

import com.example.tyfserver.donation.domain.Donation;
import java.time.LocalDateTime;

import com.example.tyfserver.donation.domain.Message;
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
    private LocalDateTime createdAt;

    public DonationResponse(Donation donation) {
        this(donation.getId(), donation.getName(), donation.getMessage(), donation.getAmount(), donation.getCreatedAt());
    }

    public DonationResponse(Long donationId, String name, String message, Long amount,
        LocalDateTime createdAt) {
        this.donationId = donationId;
        this.name = name;
        this.message = message;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public static DonationResponse forPublic(Donation donation) {
        if (donation.isSecret()) {
            return new DonationResponse(donation.getId(), Message.SECRET_NAME, Message.SECRET_MESSAGE, donation.getAmount(), donation.getCreatedAt());
        }
        return new DonationResponse(donation);
    }
}
