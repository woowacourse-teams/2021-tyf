package com.example.tyfserver.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IamPortPaymentInfo {

    private Response response;

    public IamPortPaymentInfo(Response response) {
        this.response = response;
    }

    @NoArgsConstructor
    @Getter
    public static class Response {
        String status;
        String merchantUid;
        String amount;
        String name;
        String impUid;

        public Response(String status, String merchantUid, String amount, String name, String impUid) {
            this.status = status;
            this.merchantUid = merchantUid;
            this.amount = amount;
            this.name = name;
            this.impUid = impUid;
        }
    }
}
