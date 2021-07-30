package com.example.tyfserver.payment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCancelRequest {

    @NotBlank
    @JsonDeserialize
    private String merchantUid;

    public PaymentCancelRequest(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
