package com.example.tyfserver.member.dto;

import lombok.Getter;

@Getter
public class SignUpResponse {

    private String email;
    private String oAuthType;

    public SignUpResponse(String email, String oAuthType) {
        this.email = email;
        this.oAuthType = oAuthType;
    }
}
