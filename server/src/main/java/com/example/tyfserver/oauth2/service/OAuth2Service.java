package com.example.tyfserver.oauth2.service;

import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.oauth2.dto.OauthToken;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuth2Service {
    private static final String CLIENT_ID = "ff77c9c32b464ffb0d98f3d04f7a7a48";
    private static final String CLIENT_SECRET = "nbkptjicfWF0nNfPkjglm84lCdPzK9jd";
    private static final String REDIRECT_URL = "http://localhost:8080/oauth2/kakao/login";

    private final MemberRepository memberRepository;

    public String loginKakao(String code) {
        OauthToken oauthToken = requestAuthCode(generateAuthCodeRequest(code)).getBody();
        final String body = requestProfile(generateProfileRequest(oauthToken)).getBody();
        final String email = getEmailFromKakao(body);

        // todo JWT 발급
        return email;
    }

    private String getEmailFromKakao(final String body) {
        final JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getJSONObject("kakao_account").getString("email");
    }

    private ResponseEntity<String> requestProfile(HttpEntity<MultiValueMap<String, String>> request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(OauthToken oauthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<OauthToken> requestAuthCode(HttpEntity<MultiValueMap<String, String>> request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                OauthToken.class
        );
    }

    private HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(generateParam(code), headers);
    }

    private MultiValueMap<String, String> generateParam(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URL);
        params.add("code", code);
        params.add("client_secret", CLIENT_SECRET);
        return params;
    }
}
