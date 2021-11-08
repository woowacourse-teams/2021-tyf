package com.thankyou_for.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCompleteResponse {

    private long point;

    public PaymentCompleteResponse(long point) {
        this.point = point;
    }
}
