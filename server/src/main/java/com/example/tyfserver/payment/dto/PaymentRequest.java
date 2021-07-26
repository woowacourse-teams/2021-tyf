package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRequest {

    private String impUid;
    private Long merchantUid;

    public PaymentRequest(String impUid, Long merchantUid) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
    }
}
