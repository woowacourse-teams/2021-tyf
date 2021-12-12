package com.thankyou_for.auth.domain;

import org.json.JSONObject;

public interface Oauth2 {

    String extractEmail(final JSONObject jsonObject);

    String getType();

    String getClientId();

    String getClientSecret();

    String getSignUpRedirectUrl();

    String getLoginRedirectUrl();

    String getAccessTokenApi();

    String getProfileApi();
}
