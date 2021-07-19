package com.example.tyfserver.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@Getter
@RequiredArgsConstructor
@ConfigurationProperties("kakao")
public class KakaoOauth2 implements Oauth2 {

    private final String type;
    private final String clientId;
    private final String clientSecret;
    private final String signUpRedirectUrl;
    private final String loginRedirectUrl;
    private final String accessTokenApi;
    private final String profileApi;

    @Override
    public String extractEmail(final JSONObject jsonObject) {
        return jsonObject.getJSONObject("kakao_account").getString("email");
    }
}
