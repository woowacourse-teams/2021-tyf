package com.example.tyfserver.payment.service;

import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.dto.PaymentRequest;
import com.example.tyfserver.payment.dto.PaymentResponse;
import com.example.tyfserver.payment.dto.PaymentSaveRequest;
import com.example.tyfserver.payment.dto.PaymentSaveResponse;
import com.example.tyfserver.payment.repository.PaymentRepository;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
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
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentsService {

    private static final String IAMPORT_API_URL = "https://api.iamport.kr";

    @Value("${iamport.rest_api_key}")
    private String impKey;

    @Value("${iamport.rest_api_secret}")
    private String impSecret;

    private final PaymentRepository paymentRepository;

    private final MemberRepository memberRepository;

    public PaymentSaveResponse createPayment(PaymentSaveRequest saveRequest) {
        Member creator = memberRepository
            .findByNickname(saveRequest.getCreatorNickname()) //todo: creator를 식별하는 값은 뭘 받아와야할까?
            .orElseThrow(MemberNotFoundException::new);

        Payment payment = new Payment(saveRequest.getAmount(), saveRequest.getEmail(),
            creator.getId());
        paymentRepository.save(payment);

        return new PaymentSaveResponse(payment);
        // todo: PaymentSaveResponse에 어떤 필드값들이 있으면 좋을까? (일단은 정말 필요한 값인 merchantUid만 추가!)
    }

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
        String body = ApiSender.send(
            IAMPORT_API_URL + "/users/getToken",
            HttpMethod.POST,
            accessTokenRequest()
        );
        return extractAccessToken(body);
    }

    private String extractAccessToken(String accessTokenBody) {
        JSONObject response = new JSONObject(accessTokenBody).getJSONObject("response");
        return response.getString("access_token");
    }

    private HttpEntity<String> accessTokenRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imp_key", impKey);
        jsonObject.put("imp_secret", impSecret);
        return new HttpEntity(jsonObject.toString(), headers);
    }

    private HttpEntity<Void> paymentInfoRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);
        return new HttpEntity<>(headers);

    }
}
