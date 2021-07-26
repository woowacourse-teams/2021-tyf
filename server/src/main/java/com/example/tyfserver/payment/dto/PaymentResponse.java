package com.example.tyfserver.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentResponse {
    private Long merchantUid;
    // todo 응답 필드 프론트와 협의

    public PaymentResponse(Long merchantUid) {
        this.merchantUid = merchantUid;
    }
}
