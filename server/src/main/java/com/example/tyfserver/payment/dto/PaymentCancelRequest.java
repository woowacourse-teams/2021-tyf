package com.example.tyfserver.payment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelRequest {

    @NotNull
    @JsonDeserialize
    private UUID merchantUid;

    public PaymentCancelRequest(UUID merchantUid) {
        this.merchantUid = merchantUid;
    }
}
