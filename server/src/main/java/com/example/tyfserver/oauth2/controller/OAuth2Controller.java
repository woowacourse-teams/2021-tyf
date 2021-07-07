package com.example.tyfserver.oauth2.controller;

import com.example.tyfserver.oauth2.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauth2") //todo url 정하기
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    /**
     * 리다이렉트 Url
     * https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=ff77c9c32b464ffb0d98f3d04f7a7a48&redirect_uri=http://localhost:8080/oauth2/kakao/login
     */
    @GetMapping("/kakao/login")
    public ResponseEntity<String> loginKakao(@RequestParam String code) {
        final String email = oAuth2Service.login(code, OAuth2Type.KAKAO);
        return ResponseEntity.ok(email);
    }

    @GetMapping("google/login")
    public ResponseEntity<String> loginGoogle(@RequestParam String code) {
        final String email = oAuth2Service.login(code, OAuth2Type.GOOGLE);
        return ResponseEntity.ok(email);
    }
}
