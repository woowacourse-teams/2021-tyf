package com.example.tyfserver.auth.domain;

import java.util.Arrays;

public enum OAuth2Type {

    GOOGLE, NAVER, KAKAO;

    private OAuth2 oAuth2;

    public static OAuth2Type findOAuth2Type(String type) {
        return Arrays.stream(OAuth2Type.values())
                .filter(value -> value.name().equals(type))
                .findAny()
                .orElseThrow(() -> new RuntimeException("type not found"));//todo: error 컨벤션
    }

    public static OAuth2 findOAuth2(String type) {
        return findOAuth2Type(type).oAuth2;
    }

    public void setoAuth2TypeInterface(OAuth2 oAuth2) {
        this.oAuth2 = oAuth2;
    }
}
