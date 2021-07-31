package com.example.tyfserver.payment.dto;

import com.example.tyfserver.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentPendingResponse {

    private UUID merchantUid;

    public PaymentPendingResponse(UUID merchantUid) {
        this.merchantUid = merchantUid;
    }

    public PaymentPendingResponse(Payment payment) {
        this(payment.getMerchantUid());
    }
}
