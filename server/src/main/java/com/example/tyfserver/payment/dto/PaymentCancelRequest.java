package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelRequest {

    @NotBlank
    private UUID merchantUid;

    public PaymentCancelRequest(UUID merchantUid) {
        this.merchantUid = merchantUid;
    }
}
