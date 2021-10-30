package com.example.tyfserver.payment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountInfo {

    private int code;
    private String message;
    private AccountInfoResponse response;

    public AccountInfo(int code, String message, AccountInfoResponse response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }
}
