package com.thankyou_for.auth.config;

import com.thankyou_for.auth.domain.GoogleOauth2;
import com.thankyou_for.auth.domain.KakaoOauth2;
import com.thankyou_for.auth.domain.NaverOauth2;
import com.thankyou_for.auth.domain.Oauth2Type;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {GoogleOauth2.class, KakaoOauth2.class, NaverOauth2.class})
public class Oauth2Config {

    private final GoogleOauth2 googleOauth2;
    private final KakaoOauth2 kakaoOauth2;
    private final NaverOauth2 naverOauth2;

    @PostConstruct
    public void inject() {
        for (Oauth2Type oauth2Type : Oauth2Type.values()) {
            if (oauth2Type.equals(Oauth2Type.GOOGLE)) {
                oauth2Type.setOauth2TypeInterface(googleOauth2);
            }
            if (oauth2Type.equals(Oauth2Type.NAVER)) {
                oauth2Type.setOauth2TypeInterface(naverOauth2);
            }
            if (oauth2Type.equals(Oauth2Type.KAKAO)) {
                oauth2Type.setOauth2TypeInterface(kakaoOauth2);
            }
        }
    }
}
