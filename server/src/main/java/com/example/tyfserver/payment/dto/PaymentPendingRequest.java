package com.example.tyfserver.payment.dto;

import com.example.tyfserver.payment.domain.Item;
import com.example.tyfserver.payment.util.Enum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PaymentPendingRequest {

    @Enum(enumClass = Item.class, ignoreCase = true)
    @NotBlank
    private String itemId;

    public PaymentPendingRequest(String itemId) {
        this.itemId = itemId;
    }
}
