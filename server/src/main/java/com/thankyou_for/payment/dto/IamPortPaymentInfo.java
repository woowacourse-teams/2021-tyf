package com.thankyou_for.payment.dto;

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
        String merchant_uid;
        String amount;
        String name;
        String imp_uid;

        public Response(String status, String merchant_uid, String amount, String name, String imp_uid) {
            this.status = status;
            this.merchant_uid = merchant_uid;
            this.amount = amount;
            this.name = name;
            this.imp_uid = imp_uid;
        }
    }
}
