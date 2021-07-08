package com.example.tyfserver.oauth2.dto;

public class Oauth2Token {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

    public Oauth2Token() {
    }

    public Oauth2Token(final String access_token, final String token_type, final String refresh_token, final int expires_in, final String scope, final int refresh_token_expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.refresh_token_expires_in = refresh_token_expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getScope() {
        return scope;
    }

    public int getRefresh_token_expires_in() {
        return refresh_token_expires_in;
    }

    @Override
    public String toString() {
        return "Oauth2Token{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", scope='" + scope + '\'' +
                ", refresh_token_expires_in=" + refresh_token_expires_in +
                '}';
    }
}
