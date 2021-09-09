package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PaymentPendingRequest {

    @NotBlank
    private String itemId;

    public PaymentPendingRequest(String itemId) {
        this.itemId = itemId;
    }
}
