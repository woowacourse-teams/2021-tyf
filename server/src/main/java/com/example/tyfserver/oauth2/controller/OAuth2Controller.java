package com.example.tyfserver.oauth2.controller;

import com.example.tyfserver.oauth2.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    // todo get? post?
    @GetMapping("/login/kakao")
    public ResponseEntity<String> loginKakao(@RequestParam String code) {
        final String email = oAuth2Service.loginKakao(code);
        return ResponseEntity.ok(email);
    }

    @GetMapping("/login/google")
    public ResponseEntity<String> loginGoogle(@RequestParam String code) {
        final String email = oAuth2Service.loginGoogle(code);
        return ResponseEntity.ok(email);
    }

    @GetMapping("/login/naver")
    public ResponseEntity<String> loginNaver(@RequestParam String code) {
        final String email = oAuth2Service.loginNaver(code);
        return ResponseEntity.ok(email);
    }

    // todo get? post?
    @GetMapping("/signup/kakao")
    public ResponseEntity<String> signUpKakao(@RequestParam String code) {
        final String email = oAuth2Service.signUpKakao(code);
        return ResponseEntity.ok(email);
    }

    @GetMapping("/signup/google")
    public ResponseEntity<String> signUpGoogle(@RequestParam String code) {
        final String email = oAuth2Service.signUpGoogle(code);
        return ResponseEntity.ok(email);
    }

    @GetMapping("/signup/naver")
    public ResponseEntity<String> signUpNaver(@RequestParam String code) {
        final String email = oAuth2Service.signUpNaver(code);
        return ResponseEntity.ok(email);
    }


}
