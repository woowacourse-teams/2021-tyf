package com.example.tyfserver.oauth2.domain;

import org.json.JSONObject;

public class Naver extends Oauth2Type {

    public Naver(
            final String clientId,
            final String clientSecret,
            final String redirectUrl,
            final String accessTokenApi,
            final String profileApi) {
        super(clientId, clientSecret, redirectUrl, accessTokenApi, profileApi);
    }

    @Override
    public String extractEmail(final JSONObject jsonObject) {
        // todo
        return null;
    }
}
