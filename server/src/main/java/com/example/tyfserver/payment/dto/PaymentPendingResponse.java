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
    private String itemName;
    private long amount;
    private String email;

    public PaymentPendingResponse(UUID merchantUid, String itemName, long amount, String email) {
        this.merchantUid = merchantUid;
        this.itemName = itemName;
        this.amount = amount;
        this.email = email;
    }

    public PaymentPendingResponse(Payment payment) {
        this(payment.getMerchantUid(), payment.getItemName(), payment.getAmount(), payment.getEmail());
    }
}

