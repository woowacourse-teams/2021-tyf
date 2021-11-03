package com.thankyou_for.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorWithTokenResponse extends ErrorResponse {

    private String token;

    public ErrorWithTokenResponse(String errorCode, String message, String token) {
        super(errorCode, message);
        this.token = token;
    }
}
