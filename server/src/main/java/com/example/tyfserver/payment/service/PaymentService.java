package com.example.tyfserver.payment.service;

import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.service.DonationService;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.domain.PaymentStatus;
import com.example.tyfserver.payment.dto.PaymentRequest;
import com.example.tyfserver.payment.dto.PaymentResponse;
import com.example.tyfserver.payment.dto.PaymentSaveRequest;
import com.example.tyfserver.payment.dto.PaymentSaveResponse;
import com.example.tyfserver.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private static final String IAMPORT_API_URL = "https://api.iamport.kr";

    @Value("${iamport.rest_api_key}")
    private String impKey;

    @Value("${iamport.rest_api_secret}")
    private String impSecret;

    private final PaymentRepository paymentRepository;

    private final MemberRepository memberRepository;

    public PaymentSaveResponse createPayment(PaymentSaveRequest saveRequest) {
        Member creator = memberRepository
            .findByPageName(saveRequest.getPageName())
            .orElseThrow(MemberNotFoundException::new);

        Payment payment = new Payment(saveRequest.getAmount(), saveRequest.getEmail(),
            creator.getPageName());
        paymentRepository.save(payment);

        return new PaymentSaveResponse(payment);
        // todo: PaymentSaveResponse에 어떤 필드값들이 있으면 좋을까? (일단은 정말 필요한 값인 merchantUid만 추가!)
    }

    public Payment completePayment(PaymentRequest paymentRequest) {
        // 아임포트 서버에서 API 엑세스 토큰을 발급 - REST API키, REST API Secret 필요
        String accessToken = getAccessToken();

        // 엑세스 토큰, imp_uid 로 결제정보 조회
        String paymentData = ApiSender.send(
            "https://api.iamport.kr/payments/" + paymentRequest.getImpUid(),
            HttpMethod.POST,
            paymentInfoRequest(accessToken)
        );

        JSONObject iamportPayment = new JSONObject(paymentData).getJSONObject("response");
        Payment payment = paymentRepository.findById(paymentRequest.getMerchantUid()).orElseThrow(() -> {
            throw new RuntimeException();
        });

        validatePaymentInfo(iamportPayment, payment);
        payment.complete(iamportPayment.getString("imp_uid"));
        return payment;
    }


    private void validatePaymentInfo(JSONObject paymentData, Payment payment) {
        if (!"paid".equals(paymentData.getString("status"))) {
            payment.updateStatus(paymentData.getString("status"));
            throw new RuntimeException();
        }

        if (!paymentData.getString("merchant_uid").equals(payment.getId().toString())) {
            payment.updateStatus(PaymentStatus.INVALID);
            throw new RuntimeException();
        }

        if (!payment.getAmount().equals(paymentData.getLong("amount"))) {
            payment.updateStatus(PaymentStatus.INVALID);
            throw new RuntimeException();
        }

        if (!payment.getPageName().equals(paymentData.getString("name"))) {
            payment.updateStatus(PaymentStatus.INVALID);
            throw new RuntimeException();
        }
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
