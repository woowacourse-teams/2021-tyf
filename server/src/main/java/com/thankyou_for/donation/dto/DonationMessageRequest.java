package com.thankyou_for.donation.dto;

import com.thankyou_for.donation.domain.Message;
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
    @Length(min = 1, max = 200)
    private String message;
    @NotNull
    private boolean secret;

    public DonationMessageRequest(String message, boolean secret) {
        this.message = message;
        this.secret = secret;
    }

    public Message toEntity(String name) {
        return new Message(name, message, secret);
    }
}
