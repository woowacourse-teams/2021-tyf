package com.example.tyfserver.payment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountInfo {

    private int code;
    private String message;
    private Response response;

    public AccountInfo(int code, String message, Response response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @NoArgsConstructor
    @Getter
    public static class Response {

        private String bank_holder;

        public Response(String bank_holder) {
            this.bank_holder = bank_holder;
        }
    }
}
