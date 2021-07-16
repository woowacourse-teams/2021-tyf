package com.example.tyfserver.auth.controller;

import com.example.tyfserver.auth.dto.Oauth2Request;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.exception.SignUpRequestException;
import com.example.tyfserver.auth.service.Oauth2Service;
import com.example.tyfserver.member.dto.SignUpReadyResponse;
import com.example.tyfserver.member.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final Oauth2Service oauth2Service;

    @GetMapping("/login/{oauth}")
    public ResponseEntity<TokenResponse> login(@PathVariable String oauth, @RequestParam String code) {
        return ResponseEntity.ok(oauth2Service.login(Oauth2Request.generateLoginInfoFrom(oauth), code));
    }

    @GetMapping("/signup/ready/{oauth}")
    public ResponseEntity<SignUpReadyResponse> readySignUp(@PathVariable String oauth, @RequestParam String code) {

        return ResponseEntity.ok(oauth2Service.readySignUp(Oauth2Request.generateSignUpInfoFrom(oauth), code));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            throw new SignUpRequestException();
        }
        return ResponseEntity.ok(oauth2Service.signUp(signUpRequest));
    }
}
