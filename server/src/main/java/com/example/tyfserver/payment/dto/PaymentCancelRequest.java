package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelRequest {

    private Long merchantUid;

    public PaymentCancelRequest(Long merchantUid) {
        this.merchantUid = merchantUid;
    }
}
