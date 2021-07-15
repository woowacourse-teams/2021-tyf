package com.example.tyfserver.auth.controller;

import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.exception.AlreadyRegisteredInSameOath2TypeException;
import com.example.tyfserver.auth.service.Oauth2Service;
import com.example.tyfserver.member.dto.SignUpRequest;
import com.example.tyfserver.member.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final Oauth2Service oauth2Service;

    @GetMapping("/login/{oauth}")
    public ResponseEntity<TokenResponse> login(@PathVariable String oauth, @RequestParam String code) {
        return ResponseEntity.ok(oauth2Service.login(oauth, code));
    }

    @GetMapping("/signup/ready/{oauth}")
    public ResponseEntity<SignUpResponse> readySignUp(@PathVariable String oauth, @RequestParam String code) {
        return ResponseEntity.ok(oauth2Service.readySignUp(oauth, code));
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(oauth2Service.signUp(signUpRequest));
    }
}
