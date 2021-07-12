package com.example.tyfserver.auth.controller;

import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.service.OAuth2Service;
import com.example.tyfserver.member.dto.SignUpRequest;
import com.example.tyfserver.member.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/login/{oauth}")
    public ResponseEntity<TokenResponse> login(@PathVariable String oauth, @RequestParam String code) {
        return ResponseEntity.ok(oAuth2Service.login(oauth, code));
    }

    @GetMapping("/signup/ready/{oauth}")
    public ResponseEntity<SignUpResponse> readySignUp(@PathVariable String oauth, @RequestParam String code) {
        return ResponseEntity.ok(oAuth2Service.readySignUp(oauth, code));
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(oAuth2Service.signUp(signUpRequest));
    }
}
