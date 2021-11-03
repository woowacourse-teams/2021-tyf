package com.thankyou_for.payment.util;

import com.thankyou_for.common.exception.BaseException;
import com.thankyou_for.common.util.ApiSender;
import com.thankyou_for.member.exception.AccountInvalidException;
import com.thankyou_for.payment.domain.AccountInfo;
import com.thankyou_for.payment.domain.PaymentInfo;
import com.thankyou_for.payment.domain.PaymentServiceConnector;
import com.thankyou_for.payment.domain.PaymentStatus;
import com.thankyou_for.payment.dto.IamPortPaymentInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Component
@Profile("!performance")
public class IamPortPaymentServiceConnector implements PaymentServiceConnector {

    private static final String MODULE_NAME = "아임포트";
    private static final String IAMPORT_API_URL = "https://api.iamport.kr";

    @Value("${iamport.rest_api_key}")
    private String impKey;

    @Value("${iamport.rest_api_secret}")
    private String impSecret;

    @Override
    public PaymentInfo requestPaymentInfo(UUID merchantUid) {
        String accessToken = getAccessToken();
        IamPortPaymentInfo paymentInfo = requestPaymentInfo(merchantUid, accessToken);

        return convertToPaymentInfo(paymentInfo);
    }

    private IamPortPaymentInfo requestPaymentInfo(UUID merchantUid, String accessToken) {
        return ApiSender.send(
                IAMPORT_API_URL + "/payments/find/" + merchantUid,
                HttpMethod.POST,
                paymentInfoRequest(accessToken),
                IamPortPaymentInfo.class
        );
    }

    private HttpEntity<Void> paymentInfoRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        return new HttpEntity<>(headers);
    }

    @Override
    public PaymentInfo requestPaymentRefund(UUID merchantUid) {
        String accessToken = getAccessToken();
        IamPortPaymentInfo paymentInfo = requestPaymentCancel(merchantUid, accessToken);

        return convertToPaymentInfo(paymentInfo);
    }

    private IamPortPaymentInfo requestPaymentCancel(UUID merchantUid, String accessToken) {
        return ApiSender.send(
                IAMPORT_API_URL + "/payments/cancel",
                HttpMethod.POST,
                paymentCancelRequest(accessToken, merchantUid),
                IamPortPaymentInfo.class
        );
    }

    private HttpEntity<String> paymentCancelRequest(String accessToken, UUID merchantUid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("merchant_uid", merchantUid);

        return new HttpEntity<>(jsonObject.toString(), headers);
    }

    @Override
    public String requestHolderNameOfAccount(String bankCode, String bankNum) {
        String accessToken = getAccessToken();
        return holderNameOfAccount(bankCode, bankNum, accessToken)
                .getResponse()
                .getBank_holder();
    }

    private AccountInfo holderNameOfAccount(String bankCode, String bankNum, String accessToken) {
        try {
            return ApiSender.send(
                    IAMPORT_API_URL + "/vbanks/holder?bank_code=" + bankCode + "&" + "bank_num=" + bankNum,
                    HttpMethod.GET,
                    holderNameOfAccountRequest(accessToken),
                    AccountInfo.class
            );
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new AccountInvalidException();
            }
            int status = e.getRawStatusCode();
            throw new BaseException("error-002", "계좌인증API오류: " + status);
        }
    }

    private HttpEntity<String> holderNameOfAccountRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        return new HttpEntity<>(headers);
    }

    private String getAccessToken() {
        String body = ApiSender.send(
                IAMPORT_API_URL + "/users/getToken",
                HttpMethod.POST,
                accessTokenRequest()
        );
        return extractAccessToken(body);
    }

    private PaymentInfo convertToPaymentInfo(IamPortPaymentInfo iamPortPaymentInfo) {
        IamPortPaymentInfo.Response response = iamPortPaymentInfo.getResponse();

        return new PaymentInfo(
                UUID.fromString(response.getMerchant_uid()),
                PaymentStatus.valueOf(response.getStatus().toUpperCase()),
                Long.parseLong(response.getAmount()),
                response.getName(),
                response.getImp_uid(),
                MODULE_NAME);
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
}
