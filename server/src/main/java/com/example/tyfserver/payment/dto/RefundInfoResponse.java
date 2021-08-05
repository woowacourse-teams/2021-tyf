package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundInfoResponse {

    @NotNull
    private String merChantUid;

    @NotNull
    private String verificationCode;

    public RefundInfoResponse(@NotNull String merChantUid, @NotNull String verificationCode) {
        this.merChantUid = merChantUid;
        this.verificationCode = verificationCode;
    }
}
