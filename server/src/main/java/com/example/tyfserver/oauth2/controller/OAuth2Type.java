package com.example.tyfserver.oauth2.controller;

import org.json.JSONObject;

import java.util.function.Function;

public enum OAuth2Type {
    GOOGLE(
            "153785509866-72pebue5t5qqcpci2d1bncrh497ootcc.apps.googleusercontent.com",
            "rAkMzLOEYlIRBMa6vp6DkD0O",
            "http://localhost:8080/oauth2/google/login",
            "https://oauth2.googleapis.com/token",
            "https://www.googleapis.com/oauth2/v2/userinfo",
            jsonObject -> jsonObject.getString("email")
    ),
    KAKAO(
            "ff77c9c32b464ffb0d98f3d04f7a7a48",
            "nbkptjicfWF0nNfPkjglm84lCdPzK9jd",
            "http://localhost:8080/oauth2/kakao/login",
            "https://kauth.kakao.com/oauth/token",
            "https://kapi.kakao.com/v2/user/me",
            jsonObject -> jsonObject.getJSONObject("kakao_account").getString("email")
    );
/*    NAVER(

    );*/

    // 액세스 토크 얻는 url
    // 프로필정보 얻는 url
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String accessTokenApi;
    private final String profileApi;

    private final Function<JSONObject, String> function;

    OAuth2Type(final String clientId, final String clientSecret, final String redirectUrl,
               final String accessTokenApi, final String profileApi, final Function<JSONObject, String> function) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.accessTokenApi = accessTokenApi;
        this.profileApi = profileApi;
        this.function = function;
    }

    public String extractEmail(JSONObject jsonObject) {
        return function.apply(jsonObject);
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getAccessTokenApi() {
        return accessTokenApi;
    }

    public String getProfileApi() {
        return profileApi;
    }
}
