package com.thankyou_for.payment.util;

import com.thankyou_for.common.util.ApiSender;
import com.thankyou_for.payment.domain.PaymentInfo;
import com.thankyou_for.payment.domain.PaymentServiceConnector;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("performance")
public class MockPaymentServiceConnector implements PaymentServiceConnector {

    @Value("${tyf_pay_api_url}")
    private String TYF_PAY_API_URL;

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

    @Override
    public String requestHolderNameOfAccount(String bankCode, String bankNum) {
        // todo 일단 급해서 아직 안함!!
        return "홍길동";
    }
}
