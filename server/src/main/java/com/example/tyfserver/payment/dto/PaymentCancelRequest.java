package com.example.tyfserver.payment.dto;

import com.example.tyfserver.payment.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelRequest {

    @UUID
    private String merchantUid;

    public PaymentCancelRequest(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
