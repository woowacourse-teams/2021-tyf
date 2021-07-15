package com.example.tyfserver.member.controller;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.NicknameValidationRequestException;
import com.example.tyfserver.member.exception.PageNameValidationRequestException;
import com.example.tyfserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/validate/pageName")
    public ResponseEntity<Void> validatePageName(@Valid @RequestBody PageNameValidationRequest request,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            throw new PageNameValidationRequestException();
        }
        memberService.validatePageName(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate/nickname")
    public ResponseEntity<Void> validateNickname(@Valid @RequestBody NicknameValidationRequest request,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            throw new NicknameValidationRequestException();
        }
        memberService.validateNickname(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{pageName}")
    public ResponseEntity<MemberResponse> memberInfo(@PathVariable String pageName) {
        return ResponseEntity.ok(memberService.findMember(pageName));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberPrivateResponse> memberPrivate(LoginMember loginMember) {
        return ResponseEntity.ok(memberService.findMemberPrivate(loginMember.getId()));
    }

    @GetMapping("/me/point")
    public ResponseEntity<PointResponse> memberPoint(LoginMember loginMember) {
        return ResponseEntity.ok(memberService.findMemberPoint(loginMember.getId()));
    }
}
