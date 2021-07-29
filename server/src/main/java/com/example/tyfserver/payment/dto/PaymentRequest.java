package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRequest {

    @NotBlank
    private String impUid;

    @NotNull
    private UUID merchantUid;

    public PaymentRequest(String impUid, UUID merchantUid) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
    }
}
