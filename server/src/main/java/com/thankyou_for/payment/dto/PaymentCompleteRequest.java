package com.thankyou_for.payment.dto;

import com.thankyou_for.payment.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCompleteRequest {

    @NotBlank
    private String impUid;

    @UUID
    private String merchantUid;

    public PaymentCompleteRequest(String impUid, String merchantUid) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
    }
}
