package com.thankyou_for.member.dto;

import lombok.Getter;

@Getter
public class SignUpReadyResponse {

    private String email;
    private String oauthType;

    public SignUpReadyResponse(String email, String oauthType) {
        this.email = email;
        this.oauthType = oauthType;
    }
}
