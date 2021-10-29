package com.example.tyfserver.donation.dto;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationResponse {

    private Long donationId;
    private String name;
    private String message;
    private String pageName;
    private Long amount;
    private LocalDateTime createdAt;

    public DonationResponse(Donation donation) {
        this(donation.getId(), donation.getName(), donation.getMessage(),
                donation.getPoint(), donation.getCreatedAt(), donation.getDonator().getPageName());
    }

    public DonationResponse(Long donationId, String name, String message, Long amount,
                            LocalDateTime createdAt, String pageName) {
        this.donationId = donationId;
        this.name = name;
        this.message = message;
        this.amount = amount;
        this.createdAt = createdAt;
        this.pageName = pageName;
    }

    public static DonationResponse forPublic(Donation donation) {
        if (donation.isSecret()) {
            return new DonationResponse(donation.getId(), Message.SECRET_NAME, Message.SECRET_MESSAGE,
                    donation.getPoint(), donation.getCreatedAt(), Message.SECRET_PAGE_NAME);
        }
        return new DonationResponse(donation);
    }
}
