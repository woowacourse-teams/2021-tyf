package com.example.tyfserver.auth.util;

import com.example.tyfserver.auth.domain.Oauth2;
import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.Oauth2Request;
import com.example.tyfserver.common.util.ApiSender;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class Oauth2ServiceConnector {

    public String getEmailFromOauth2(Oauth2Request oauth2Request, String code) {
        final String accessToken = requestAccessToken(code, oauth2Request);
        return requestEmail(accessToken, oauth2Request);
    }

    private String requestAccessToken(String code, Oauth2Request oauth2Request) {
        String body = ApiSender.send(
                oauth2Request.getAccessTokenApi(),
                HttpMethod.POST,
                generateAccessTokenRequest(code, oauth2Request)
        );

        return extractAccessToken(body);
    }

    private String requestEmail(String accessToken, Oauth2Request oauth2Request) {
        String body = ApiSender.send(
                oauth2Request.getProfileApi(),
                HttpMethod.GET,
                generateProfileRequest(accessToken)
        );

        Oauth2 oauth2 = Oauth2Type.findOauth2(oauth2Request.getType());
        return extractEmail(oauth2, body);
    }


    private HttpEntity<MultiValueMap<String, String>> generateAccessTokenRequest(String code, Oauth2Request oauth2Request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", oauth2Request.getClientId());
        params.add("redirect_uri", oauth2Request.getRedirectUrl());
        params.add("code", code);
        params.add("client_secret", oauth2Request.getClientSecret());

        return new HttpEntity<>(params, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        headers.setBearerAuth(accessToken);
        return new HttpEntity<>(headers);
    }

    private String extractEmail(Oauth2 oauth2, String response) {
        final JSONObject jsonObject = new JSONObject(response);
        return oauth2.extractEmail(jsonObject);
    }

    private String extractAccessToken(String body) {
        final JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getString("access_token");
    }
}
