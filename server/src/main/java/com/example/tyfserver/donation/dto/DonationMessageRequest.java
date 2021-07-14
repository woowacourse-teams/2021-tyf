package com.example.tyfserver.donation.dto;

import com.example.tyfserver.donation.domain.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationMessageRequest {

    private String name;
    private String message;
    private boolean secret;

    public DonationMessageRequest(String name, String message) {
        this(name, message, true);
    }

    public DonationMessageRequest(String name, String message, boolean secret) {
        this.name = name;
        this.message = message;
        this.secret = secret;
    }

    public Message toEntity() {
        return new Message(name, message, secret);
    }
}
