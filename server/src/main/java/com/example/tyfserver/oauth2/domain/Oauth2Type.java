package com.example.tyfserver.oauth2.domain;

import org.json.JSONObject;

public abstract class Oauth2Type {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String accessTokenApi;
    private final String profileApi;

    public Oauth2Type(String clientId, String clientSecret, String redirectUrl, String accessTokenApi, String profileApi) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.accessTokenApi = accessTokenApi;
        this.profileApi = profileApi;
    }

    public abstract String extractEmail(JSONObject jsonObject);

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
