package com.example.tyfserver.oauth2.service;

import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.oauth2.controller.OAuth2Type;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuth2Service {
    private final MemberRepository memberRepository;

    public String login(String code, OAuth2Type oAuth2Type) {
        final String accessToken = requestAccessToken(code, oAuth2Type);
        final String email = requestEmail(accessToken, oAuth2Type);

        memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("가입되어 있지 않은 유저")); // todo 예외클래스, 메시지

        // todo JWT 발급
        return email;
    }

    private String requestAccessToken(String code, OAuth2Type oAuth2Type) {
        final RestTemplate restTemplate = new RestTemplate();

        final String body = restTemplate.postForObject(
                oAuth2Type.getAccessTokenApi(),
                generateAccessTokenRequest(code, oAuth2Type),
                String.class
        );

        final JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getString("access_token");
    }

    private HttpEntity<MultiValueMap<String, String>> generateAccessTokenRequest(String code, OAuth2Type oAuth2Type) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", oAuth2Type.getClientId());
        params.add("redirect_uri", oAuth2Type.getRedirectUrl());
        params.add("code", code);
        params.add("client_secret", oAuth2Type.getClientSecret());

        return new HttpEntity<>(params, headers);
    }

    private String requestEmail(String accessToken, OAuth2Type oAuth2Type) {
        RestTemplate restTemplate = new RestTemplate();

        final String body = restTemplate.exchange(
                oAuth2Type.getProfileApi(),
                HttpMethod.GET,
                generateProfileRequest(accessToken),
                String.class
        ).getBody();

        return extractEmail(oAuth2Type, body);
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    private String extractEmail(OAuth2Type oAuth2Type, String response) {
        final JSONObject jsonObject = new JSONObject(response);
        return oAuth2Type.extractEmail(jsonObject);
    }
}
