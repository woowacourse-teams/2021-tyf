package com.example.tyfserver.auth.dto;

import com.example.tyfserver.auth.domain.Oauth2;
import com.example.tyfserver.auth.domain.Oauth2Type;
import lombok.Getter;

@Getter
public class Oauth2InfoDto {

    private String type;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String accessTokenApi;
    private String profileApi;


    private Oauth2InfoDto(String type, String clientId, String clientSecret, String redirectUrl, String accessTokenApi,
                         String profileApi) {
        this.type = type;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.accessTokenApi = accessTokenApi;
        this.profileApi = profileApi;
    }

    public static Oauth2InfoDto generateLoginInfoFrom (String oauthType) {
        Oauth2 oauth = Oauth2Type.findOauth2(oauthType);
        return new Oauth2InfoDto(oauth.getType(), oauth.getClientId(), oauth.getClientSecret(),
                oauth.getLoginRedirectUrl(), oauth.getAccessTokenApi(), oauth.getProfileApi());
    }

    public static Oauth2InfoDto generateSignUpInfoFrom (String oauthType) {
        Oauth2 oauth = Oauth2Type.findOauth2(oauthType);
        return new Oauth2InfoDto(oauth.getType(), oauth.getClientId(), oauth.getClientSecret(),
                oauth.getSignUpRedirectUrl(), oauth.getAccessTokenApi(), oauth.getProfileApi());
    }
}
