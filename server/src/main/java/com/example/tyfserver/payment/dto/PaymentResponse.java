package com.example.tyfserver.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentResponse {

    private Long merchantUid;

    public PaymentResponse(Long merchantUid) {
        this.merchantUid = merchantUid;
    }
}
