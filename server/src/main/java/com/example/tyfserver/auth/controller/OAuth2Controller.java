package com.example.tyfserver.auth.controller;

import com.example.tyfserver.auth.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/login/{oauth}")
    public ResponseEntity<String> login(@PathVariable String oauth, @RequestParam String code) {
        final String email = oAuth2Service.login(oauth, code);
        return ResponseEntity.ok("");
    }

    // todo get? post?
    @GetMapping("/signup/{oauth}")
    public ResponseEntity<String> signUp(@PathVariable String oauth, @RequestParam String code) {
        final String email = oAuth2Service.signUp(oauth, code);
        return ResponseEntity.ok(email);
    }
}
