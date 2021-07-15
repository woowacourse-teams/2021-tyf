package com.example.tyfserver.auth.domain;

import org.json.JSONObject;

public interface Oauth2 {

    String extractEmail(final JSONObject jsonObject);

    String getType();

    String getClientId();

    String getClientSecret();

    String getRedirectUrl();

    String getAccessTokenApi();

    String getProfileApi();
}
