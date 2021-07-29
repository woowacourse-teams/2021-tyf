package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelRequest {

    private UUID merchantUid;

    public PaymentCancelRequest(UUID merchantUid) {
        this.merchantUid = merchantUid;
    }
}
