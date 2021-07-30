package com.example.tyfserver.payment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCompleteRequest {

    @NotBlank
    private String impUid;

    @NotNull
    @JsonDeserialize
    private UUID merchantUid;

    public PaymentCompleteRequest(String impUid, UUID merchantUid) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
    }
}
