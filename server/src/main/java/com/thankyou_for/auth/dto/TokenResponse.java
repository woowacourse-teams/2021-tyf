package com.thankyou_for.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
