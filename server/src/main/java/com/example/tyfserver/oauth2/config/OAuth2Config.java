package com.example.tyfserver.oauth2.config;

import com.example.tyfserver.oauth2.domain.GoogleOAuth2;
import com.example.tyfserver.oauth2.domain.KakaoOAuth2;
import com.example.tyfserver.oauth2.domain.NaverOAuth2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {GoogleOAuth2.class, KakaoOAuth2.class, NaverOAuth2.class})
public class OAuth2Config {
}
