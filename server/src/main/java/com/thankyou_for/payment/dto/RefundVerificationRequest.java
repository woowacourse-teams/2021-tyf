package com.thankyou_for.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundVerificationRequest {

    @NotNull
    private String merchantUid;

    @NotNull
    private String verificationCode;

    public RefundVerificationRequest(String merchantUid, String verificationCode) {
        this.merchantUid = merchantUid;
        this.verificationCode = verificationCode;
    }
}
