package com.thankyou_for.payment.dto;

import com.thankyou_for.payment.util.UUID;
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
