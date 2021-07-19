package com.example.tyfserver.auth.dto;

import lombok.Getter;

@Getter
public class SignUpResponse {

    private String token;
    private String pageName;

    public SignUpResponse(String token, String pageName) {
        this.token = token;
        this.pageName = pageName;
    }
}
