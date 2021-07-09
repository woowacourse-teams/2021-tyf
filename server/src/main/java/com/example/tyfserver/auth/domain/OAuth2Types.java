package com.example.tyfserver.auth.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuth2Types {
    private final List<OAuth2Type> OAuth2TypeList;

    public OAuth2Type getOauth2Type(String oauth) {
        return OAuth2TypeList.stream()
                .filter(oauth2Type -> oauth2Type.getType().equals(oauth))
                .findAny()
                .orElseThrow(() -> new RuntimeException("등록되지 않은 oauth 서비스"));
    }
}
