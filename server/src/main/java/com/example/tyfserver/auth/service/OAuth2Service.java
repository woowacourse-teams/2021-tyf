package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.domain.OAuth2;
import com.example.tyfserver.auth.domain.OAuth2Type;
import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.member.repository.MemberRepository;
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

    // todo 리턴타입 JWT로
    public String login(final String oauth, final String code) {
        final OAuth2 oAuth2 = OAuth2Type.findOAuth2Type(oauth);
        final String accessToken = requestAccessToken(code, oAuth2);
        final String email = requestEmail(accessToken, oAuth2);

        memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("가입되어 있지 않은 유저")); // todo 예외클래스, 메시지

        // todo JWT 발급
        return email;
    }

    public String signUp(final String oauth, final String code) {
        final OAuth2 oAuth2 = OAuth2Type.findOAuth2Type(oauth);
        final String accessToken = requestAccessToken(code, oAuth2);
        final String email = requestEmail(accessToken, oAuth2);

        // 멤버가 있는지 확인
        // 1. 멤버가 없음: 가입
        // 2. 멤버가 있고 다른 타입: 어떤 타입으로 가입되어있는지 안내
        // 3. 멤버가 있고 같은 타입: 이미 가입되어있다 안내하고 로그인

        // todo JWT 발급
        return email;
    }

    private String requestEmail(String accessToken, OAuth2 oAuth2) {
        String body = ApiSender.send(
                oAuth2.getProfileApi(),
                HttpMethod.GET,
                generateProfileRequest(accessToken)
        );

        return extractEmail(oAuth2, body);
    }

    private String requestAccessToken(String code, OAuth2 oAuth2) {
        String body = ApiSender.send(
                oAuth2.getAccessTokenApi(),
                HttpMethod.POST,
                generateAccessTokenRequest(code, oAuth2)
        );

        return extractAccessToken(body);
    }

    private HttpEntity<MultiValueMap<String, String>> generateAccessTokenRequest(String code, OAuth2 oAuth2) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", oAuth2.getClientId());
        params.add("redirect_uri", oAuth2.getRedirectUrl());
        params.add("code", code);
        params.add("client_secret", oAuth2.getClientSecret());

        return new HttpEntity<>(params, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    private String extractEmail(OAuth2 oAuth2, String response) {
        final JSONObject jsonObject = new JSONObject(response);
        return oAuth2.extractEmail(jsonObject);
    }

    private String extractAccessToken(String body) {
        final JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getString("access_token");
    }
}
