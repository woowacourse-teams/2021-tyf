package com.example.tyfserver.auth.domain;

import java.util.Arrays;

public enum OAuth2Type {

    GOOGLE, NAVER, KAKAO;

    private OAuth2 oAuth2;

    public void setoAuth2TypeInterface(OAuth2 oAuth2) {
        this.oAuth2 = oAuth2;
    }

    public static OAuth2 findOAuth2Type(String type) {
        return Arrays.stream(OAuth2Type.values())
                .filter(value -> value.name().equals(type.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("type not found"))//todo: error 컨벤션
                .oAuth2;
    }
}
