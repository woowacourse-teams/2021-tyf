package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentRefundResponse {

    private String email;

    public PaymentRefundResponse(String email) {
        this.email = email;
    }
}
