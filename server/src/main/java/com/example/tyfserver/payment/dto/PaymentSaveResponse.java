package com.example.tyfserver.payment.dto;

import com.example.tyfserver.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentSaveResponse {

    private Long merchantUid;

    public PaymentSaveResponse(Payment payment) {
        merchantUid = payment.getId();
    }
}
