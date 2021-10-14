package com.example.tyfserver.payment.util;

import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.payment.domain.PaymentInfo;
import com.example.tyfserver.payment.domain.PaymentServiceConnector;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.UUID;

public class MockPaymentServiceConnector implements PaymentServiceConnector {

    private static final String TYF_PAY_API_URL = "http://13.124.232.206:8080/tyfpay";

    @Override
    public PaymentInfo requestPaymentInfo(UUID merchantUid) {
        return requestPaymentInfoToMockServer(merchantUid);
    }

    private PaymentInfo requestPaymentInfoToMockServer(UUID merchantUid) {
        return ApiSender.send(
                TYF_PAY_API_URL + "/payments/find/" + merchantUid,
                HttpMethod.POST,
                paymentInfoRequest(),
                PaymentInfo.class
        );
    }

    private HttpEntity<Void> paymentInfoRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    @Override
    public PaymentInfo requestPaymentRefund(UUID merchantUid) {
        return requestPaymentCancel(merchantUid);
    }

    private PaymentInfo requestPaymentCancel(UUID merchantUid) {
        return ApiSender.send(
                TYF_PAY_API_URL + "/payments/cancel",
                HttpMethod.POST,
                paymentCancelRequest(merchantUid),
                PaymentInfo.class
        );
    }

    private HttpEntity<String> paymentCancelRequest(UUID merchantUid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("merchant_uid", merchantUid);

        return new HttpEntity<>(jsonObject.toString(), headers);
    }
}
