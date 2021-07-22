package com.example.tyfserver.member.controller;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.NicknameValidationRequestException;
import com.example.tyfserver.member.exception.PageNameValidationRequestException;
import com.example.tyfserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;

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

    @PostMapping("/validate/token")
    public ResponseEntity<Void> validateToken(@RequestBody TokenValidationRequest request) {
        authenticationService.validateToken(request.getToken());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{pageName}")
    public ResponseEntity<MemberResponse> memberInfo(@PathVariable String pageName) {
        return ResponseEntity.ok(memberService.findMember(pageName));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDetailResponse> memberDetail(LoginMember loginMember) {
        return ResponseEntity.ok(memberService.findMemberDetail(loginMember.getId()));
    }

    @GetMapping("/me/point")
    public ResponseEntity<PointResponse> memberPoint(LoginMember loginMember) {
        return ResponseEntity.ok(memberService.findMemberPoint(loginMember.getId()));
    }

    @GetMapping("/curations")
    public ResponseEntity<List<CurationsResponse>> curations() {
        return ResponseEntity.ok(memberService.findCurations());
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileResponse> profiles(@RequestParam MultipartFile multipartFile, LoginMember loginMember) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.upload(multipartFile, loginMember));
    }
}
