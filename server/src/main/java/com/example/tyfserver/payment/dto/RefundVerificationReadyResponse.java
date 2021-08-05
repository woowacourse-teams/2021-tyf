package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundVerificationReadyResponse {

    private String email;

    public RefundVerificationReadyResponse(String email) {
        this.email = email;
    }
}
