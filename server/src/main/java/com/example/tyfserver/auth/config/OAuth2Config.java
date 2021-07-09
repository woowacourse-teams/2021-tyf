package com.example.tyfserver.auth.config;

import com.example.tyfserver.auth.domain.GoogleOAuth2;
import com.example.tyfserver.auth.domain.KakaoOAuth2;
import com.example.tyfserver.auth.domain.NaverOAuth2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {GoogleOAuth2.class, KakaoOAuth2.class, NaverOAuth2.class})
public class OAuth2Config {
}
