package com.example.tyfserver.member.controller;

import com.example.tyfserver.member.dto.NickNameValidationRequest;
import com.example.tyfserver.member.dto.UrlValidationRequest;
import com.example.tyfserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/validate/landingpage-url")
    public ResponseEntity<Void> validateLandingPageUrl( @Valid @RequestBody UrlValidationRequest request) {
        memberService.validateUrl(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate/nickname")
    public ResponseEntity<Void> validateNickName(@Valid @RequestBody NickNameValidationRequest request) {
        memberService.validateNickName(request);
        return ResponseEntity.ok().build();
    }

}
