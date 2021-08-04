package com.example.tyfserver.payment.dto;

import com.example.tyfserver.payment.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentRefundRequest {

    @UUID
    private String merchantUid;

    public PaymentRefundRequest(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
