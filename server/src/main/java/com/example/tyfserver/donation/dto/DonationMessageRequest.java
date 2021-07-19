package com.example.tyfserver.donation.dto;

import com.example.tyfserver.donation.domain.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationMessageRequest {

    @NotBlank
    @Length(min = 3, max = 20)
    private String name;
    @NotBlank
    @Length(min = 1, max = 200)
    private String message;
    @NotNull
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
