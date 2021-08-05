package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundVerificationReadyResponse {

    private String email;
    private int timeout; // 인증번호 유효시간
    private int resendCoolTime; // 인증번호 입력 재시도 가능 시간

    public RefundVerificationReadyResponse(String email, int timeout, int resendCoolTime) {
        this.email = email;
        this.timeout = timeout;
        this.resendCoolTime = resendCoolTime;
    }

    public RefundVerificationReadyResponse(String email) {
        this.email = email;
    }
}
