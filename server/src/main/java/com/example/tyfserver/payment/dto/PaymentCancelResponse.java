package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelResponse {

    private UUID merchantUid;

    public PaymentCancelResponse(UUID merchantUid) {
        this.merchantUid = merchantUid;
    }
}
