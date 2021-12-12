package com.thankyou_for.auth.controller;

import com.thankyou_for.auth.dto.Oauth2Request;
import com.thankyou_for.auth.dto.SignUpResponse;
import com.thankyou_for.auth.dto.TokenResponse;
import com.thankyou_for.auth.exception.SignUpRequestException;
import com.thankyou_for.auth.service.Oauth2Service;
import com.thankyou_for.member.dto.SignUpReadyResponse;
import com.thankyou_for.member.dto.SignUpRequest;
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
        return ResponseEntity.ok(
                oauth2Service.login(Oauth2Request.generateLoginInfoFrom(oauth), code)
        );
    }

    @GetMapping("/signup/ready/{oauth}")
    public ResponseEntity<SignUpReadyResponse> readySignUp(@PathVariable String oauth, @RequestParam String code) {

        return ResponseEntity.ok(
                oauth2Service.readySignUp(Oauth2Request.generateSignUpInfoFrom(oauth), code)
        );
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
