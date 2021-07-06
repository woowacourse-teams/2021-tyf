package com.example.tyfserver.donation.dto;

import lombok.Getter;

@Getter
public class DonationMessageRequest {

    private final String name;
    private final String message;

    public DonationMessageRequest(final String name, final String message) {
        this.name = name;
        this.message = message;
    }
}
