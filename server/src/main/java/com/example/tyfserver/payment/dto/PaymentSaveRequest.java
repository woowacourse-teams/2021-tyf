package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentSaveRequest {

    private Long amount;

    private String email;

    private String creatorNickname;

    public PaymentSaveRequest(Long amount, String email, String creatorNickname) {
        this.amount = amount;
        this.email = email;
        this.creatorNickname = creatorNickname;
    }
}
