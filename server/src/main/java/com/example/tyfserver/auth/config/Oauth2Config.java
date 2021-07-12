package com.example.tyfserver.auth.config;

import com.example.tyfserver.auth.domain.GoogleOauth2;
import com.example.tyfserver.auth.domain.KakaoOauth2;
import com.example.tyfserver.auth.domain.NaverOauth2;
import com.example.tyfserver.auth.domain.Oauth2Type;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {GoogleOauth2.class, KakaoOauth2.class, NaverOauth2.class})
public class Oauth2Config {

    private final GoogleOauth2 googleOAuth2;
    private final KakaoOauth2 kakaoOAuth2;
    private final NaverOauth2 naverOAuth2;

    @PostConstruct
    public void inject() {
        for (Oauth2Type value : Oauth2Type.values()) {
            if (value.equals(Oauth2Type.GOOGLE)) {
                value.setoAuth2TypeInterface(googleOAuth2);
            }
            if (value.equals(Oauth2Type.NAVER)) {
                value.setoAuth2TypeInterface(naverOAuth2);
            }
            if (value.equals(Oauth2Type.KAKAO)) {
                value.setoAuth2TypeInterface(kakaoOAuth2);
            }
        }
    }
}
