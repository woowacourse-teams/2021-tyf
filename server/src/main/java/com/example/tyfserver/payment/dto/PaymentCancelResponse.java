package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelResponse {

    private Long merchantUid;

    public PaymentCancelResponse(Long merchantUid) {
        this.merchantUid = merchantUid;
    }
}
