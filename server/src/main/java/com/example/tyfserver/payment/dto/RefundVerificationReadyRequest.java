package com.example.tyfserver.payment.dto;

import com.example.tyfserver.payment.util.UUID;
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
