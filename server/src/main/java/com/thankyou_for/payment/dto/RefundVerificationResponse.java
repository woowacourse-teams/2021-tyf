package com.thankyou_for.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundVerificationResponse {

    private String refundAccessToken;

    public RefundVerificationResponse(String refundAccessToken) {
        this.refundAccessToken = refundAccessToken;
    }
}
