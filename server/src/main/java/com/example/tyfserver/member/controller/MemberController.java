package com.example.tyfserver.member.controller;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.member.dto.MemberResponse;
import com.example.tyfserver.member.dto.NicknameValidationRequest;
import com.example.tyfserver.member.dto.PointResponse;
import com.example.tyfserver.member.dto.UrlValidationRequest;
import com.example.tyfserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/validate/url-name")
    public ResponseEntity<Void> validateUrlName(@Valid @RequestBody UrlValidationRequest request) {
        memberService.validateUrl(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate/nickname")
    public ResponseEntity<Void> validateNickname(@Valid @RequestBody NicknameValidationRequest request) {
        memberService.validateNickname(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{urlName}")
    public ResponseEntity<MemberResponse> memberInfo(@PathVariable String urlName) {
        return ResponseEntity.ok(memberService.findMember(urlName));
    }

    @GetMapping("/point")
    public ResponseEntity<PointResponse> memberPoint(LoginMember loginMember) {
        return ResponseEntity.ok(memberService.findMemberPoint(loginMember.getEmail()));
    }
}
