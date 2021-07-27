package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentSaveRequest {

    private Long amount;

    private String email;

    private String pageName;

    public PaymentSaveRequest(Long amount, String email, String pageName) {
        this.amount = amount;
        this.email = email;
        this.pageName = pageName;
    }
}
