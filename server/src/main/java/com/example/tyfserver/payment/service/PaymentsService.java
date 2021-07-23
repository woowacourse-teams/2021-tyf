package com.example.tyfserver.payment.service;

import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.payment.dto.PaymentRequest;
import com.example.tyfserver.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentsService {

    @Value("iamport.rest_api_key")
    private String impKey;

    @Value("iamport.rest_api_secret")
    private String impSecret;

    private static final String impApiUrl = "https://api.iamport.kr";


    public PaymentResponse completePayment(PaymentRequest paymentRequest) {
        // 아임포트 서버에서 API 엑세스 토큰을 발급 - REST API키, REST API Secret 필요
        String accessToken = getAccessToken();

        // 엑세스 토큰, imp_uid 로 결제정보 조회
        String paymentData = ApiSender.send(
                "https://api.iamport.kr/payments/" + paymentRequest.getImpUid(),
                HttpMethod.POST,
                paymentInfoRequest(accessToken)
        );

        // todo 결제 위변조 여부 검증
        // 위에서 얻은 결제정보와 A단계에서 서버에 저장한 주문정보를 비교 검증

        // todo 결제 정보 DB에 저장

        // todo 결제 성공 응답
        return new PaymentResponse();
    }

    private String getAccessToken() {
        String accessTokenBody = ApiSender.send(
                impApiUrl + "/users/getToken",
                HttpMethod.POST,
                accessTokenRequest()
        );
        return new JSONObject(accessTokenBody).getJSONObject("response").getString("access_token");
    }

    private HttpEntity<MultiValueMap<String, String>> accessTokenRequest() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", impKey);
        params.add("imp_secret", impSecret);

        return new HttpEntity<>(params);
    }

    private HttpEntity<Void> paymentInfoRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);
        return new HttpEntity<>(headers);

    }
}
