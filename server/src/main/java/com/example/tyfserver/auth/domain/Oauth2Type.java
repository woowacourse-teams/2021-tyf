package com.example.tyfserver.auth.domain;

import java.util.Arrays;

public enum Oauth2Type {

    GOOGLE, NAVER, KAKAO;

    private Oauth2 oauth2;

    public static Oauth2Type findOauth2Type(String type) {
        return Arrays.stream(Oauth2Type.values())
                .filter(value -> value.name().equals(type))
                .findAny()
                .orElseThrow(() -> new RuntimeException("type not found"));//todo: error 컨벤션
    }

    public static Oauth2 findOauth2(String type) {
        return findOauth2Type(type).oauth2;
    }

    public void setOauth2TypeInterface(Oauth2 oauth2) {
        this.oauth2 = oauth2;
    }
}
