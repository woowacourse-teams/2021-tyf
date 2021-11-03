package com.thankyou_for.payment.dto;

import com.thankyou_for.payment.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundVerificationReadyRequest {

    @UUID
    private String merchantUid;

    public RefundVerificationReadyRequest(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
