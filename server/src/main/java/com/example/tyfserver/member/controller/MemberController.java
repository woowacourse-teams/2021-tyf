package com.example.tyfserver.member.controller;

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
    public ResponseEntity<Void> validateLandingPageUrl(
            @Valid @RequestBody UrlValidationRequest urlValidationRequest) {

        memberService.validateUrl(urlValidationRequest);
        return ResponseEntity.ok().build();
    }

}
