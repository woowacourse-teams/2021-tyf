package com.example.tyfserver.donation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationMessageRequest {

    private String name;
    private String message;
    private boolean isPublic;

    public DonationMessageRequest(final String name, final String message) {
        this.name = name;
        this.message = message;
    }

    public DonationMessageRequest(String name, String message, boolean isPublic) {
        this.name = name;
        this.message = message;
        this.isPublic = isPublic;
    }
}
