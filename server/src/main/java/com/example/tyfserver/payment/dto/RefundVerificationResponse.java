package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundVerificationResponse {

    // todo 액세스 토큰 네이밍. 로그인 토큰이랑 헷갈릴듯
    private String refundToken;

    public RefundVerificationResponse(String refundToken) {
        this.refundToken = refundToken;
    }
}
