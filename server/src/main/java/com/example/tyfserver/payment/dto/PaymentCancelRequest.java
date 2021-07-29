package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelRequest {

    private Long merchantUid;

    private Long cancelRequestAmount;

    public PaymentCancelRequest(Long merchantUid, Long cancelRequestAmount) {
        this.merchantUid = merchantUid;
        this.cancelRequestAmount = cancelRequestAmount;
    }
}
