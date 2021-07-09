package com.example.tyfserver.auth.domain;

import org.json.JSONObject;

public interface OAuth2 {

    public String extractEmail(final JSONObject jsonObject);

    public String getType();

    public String getClientId();

    public String getClientSecret();

    public String getRedirectUrl();

    public String getAccessTokenApi();

    public String getProfileApi();
}
