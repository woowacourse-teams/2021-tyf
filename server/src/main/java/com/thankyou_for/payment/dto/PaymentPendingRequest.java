package com.thankyou_for.payment.dto;

import com.thankyou_for.payment.domain.Item;
import com.thankyou_for.payment.util.Enum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PaymentPendingRequest {

    @Enum(enumClass = Item.class)
    @NotBlank
    private String itemId;

    public PaymentPendingRequest(String itemId) {
        this.itemId = itemId;
    }
}
