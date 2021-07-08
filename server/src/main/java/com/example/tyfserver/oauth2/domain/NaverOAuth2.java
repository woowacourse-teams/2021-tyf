package com.example.tyfserver.oauth2.domain;

import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("naver")
public class NaverOAuth2 extends OAuth2Type {

    public NaverOAuth2(
            final String type,
            final String clientId,
            final String clientSecret,
            final String redirectUrl,
            final String accessTokenApi,
            final String profileApi) {
        super(type, clientId, clientSecret, redirectUrl, accessTokenApi, profileApi);
    }

    @Override
    public String extractEmail(final JSONObject jsonObject) {
        return jsonObject.getJSONObject("response").getString("email");
    }
}
