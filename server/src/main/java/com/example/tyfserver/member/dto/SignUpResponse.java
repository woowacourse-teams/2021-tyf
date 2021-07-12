package com.example.tyfserver.member.dto;

import lombok.Getter;

@Getter
public class SignUpResponse {

    private String email;
    private String oauthType;

    public SignUpResponse(String email, String oauthType) {
        this.email = email;
        this.oauthType = oauthType;
    }
}
