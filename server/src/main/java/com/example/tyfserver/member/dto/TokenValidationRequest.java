package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenValidationRequest {

    private String token;

    public TokenValidationRequest(String token) {
        this.token = token;
    }
}
