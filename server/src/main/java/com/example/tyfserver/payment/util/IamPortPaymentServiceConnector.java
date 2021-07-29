package com.example.tyfserver.payment.util;

import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.payment.domain.PaymentInfo;
import com.example.tyfserver.payment.domain.PaymentServiceConnector;
import com.example.tyfserver.payment.domain.PaymentStatus;
import com.example.tyfserver.payment.dto.IamPortPaymentInfo;
import com.example.tyfserver.payment.dto.PaymentRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class IamPortPaymentServiceConnector implements PaymentServiceConnector {
    private static final String moduleName = "아임포트";
    private static final String IAMPORT_API_URL = "https://api.iamport.kr";

    @Value("${iamport.rest_api_key}")
    private String impKey;

    @Value("${iamport.rest_api_secret}")
    private String impSecret;

    @Override
    public PaymentInfo requestPaymentInfo(PaymentRequest paymentRequest) {
        IamPortPaymentInfo.Response response = request(paymentRequest).getResponse();
        return new PaymentInfo(
                Long.parseLong(response.getMerchant_uid()),
                PaymentStatus.valueOf(response.getStatus().toUpperCase()),
                Long.parseLong(response.getAmount()),
                response.getName(),
                response.getImp_uid(),
                moduleName);
    }

    private IamPortPaymentInfo request(PaymentRequest paymentRequest) {
        String accessToken = getAccessToken();

        return ApiSender.send(
                IAMPORT_API_URL + "/payments/" + paymentRequest.getImpUid(),
                HttpMethod.POST,
                paymentInfoRequest(accessToken),
                IamPortPaymentInfo.class
        );
    }

    private String getAccessToken() {
        String body = ApiSender.send(
                IAMPORT_API_URL + "/users/getToken",
                HttpMethod.POST,
                accessTokenRequest()
        );
        return extractAccessToken(body);
    }

    private HttpEntity<String> accessTokenRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imp_key", impKey);
        jsonObject.put("imp_secret", impSecret);

        return new HttpEntity<>(jsonObject.toString(), headers);
    }

    private String extractAccessToken(String accessTokenBody) {
        return new JSONObject(accessTokenBody)
                .getJSONObject("response")
                .getString("access_token");
    }

    private HttpEntity<Void> paymentInfoRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        return new HttpEntity<>(headers);
    }
}
