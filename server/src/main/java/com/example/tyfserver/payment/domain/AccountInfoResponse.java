package com.example.tyfserver.payment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountInfoResponse {

    private String bankHolder;

    public AccountInfoResponse(String bankHolder) {
        this.bankHolder = bankHolder;
    }
}
