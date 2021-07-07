package com.example.tyfserver.oauth2.config;

import com.example.tyfserver.oauth2.domain.Google;
import com.example.tyfserver.oauth2.domain.Kakao;
import com.example.tyfserver.oauth2.domain.Naver;
import com.example.tyfserver.oauth2.domain.Oauth2Type;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:oauth2.properties")
@RequiredArgsConstructor
public class OAuth2Config {

    private final Environment env;

    @Bean
    public Oauth2Type kakao() {
        return new Kakao(
                env.getProperty("kakao.client_id"),
                env.getProperty("kakao.client_secret"),
                env.getProperty("kakao.redirect_url"),
                env.getProperty("kakao.access_token_api"),
                env.getProperty("kakao.profile_api")
        );
    }

    @Bean
    public Oauth2Type google() {
        return new Google(
                env.getProperty("google.client_id"),
                env.getProperty("google.client_secret"),
                env.getProperty("google.redirect_url"),
                env.getProperty("google.access_token_api"),
                env.getProperty("google.profile_api")
        );
    }

    @Bean
    public Oauth2Type naver() {
        return new Naver(
                env.getProperty("naver.client_id"),
                env.getProperty("naver.client_secret"),
                env.getProperty("naver.redirect_url"),
                env.getProperty("naver.access_token_api"),
                env.getProperty("naver.profile_api")
        );
    }
}
