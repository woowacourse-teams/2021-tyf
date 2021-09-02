package com.example.tyfserver.member.controller;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.AccountRegisterValidationRequestException;
import com.example.tyfserver.member.exception.BioValidationRequestException;
import com.example.tyfserver.member.exception.NicknameValidationRequestException;
import com.example.tyfserver.member.exception.PageNameValidationRequestException;
import com.example.tyfserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> validateNickname(@Valid @RequestBody NicknameRequest request,
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
    public ResponseEntity<MemberResponse> memberDetail(LoginMember loginMember) {
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

    @PutMapping("/profile")
    public ResponseEntity<ProfileResponse> profile(@RequestParam MultipartFile profileImage, LoginMember loginMember) {
        return ResponseEntity.ok((memberService.uploadProfile(profileImage, loginMember)));
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteProfile(LoginMember loginMember) {
        memberService.deleteProfile(loginMember);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me/bio")
    public ResponseEntity<Void> updateBio(LoginMember loginMember,
                                          @Valid @RequestBody MemberBioUpdateRequest memberBioUpdateRequest,
                                          BindingResult result) {
        if (result.hasErrors()) {
            throw new BioValidationRequestException();
        }

        memberService.updateBio(loginMember, memberBioUpdateRequest.getBio());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me/nickname")
    public ResponseEntity<Void> updateNickname(LoginMember loginMember,
                                               @Valid @RequestBody NicknameRequest nicknameRequest,
                                               BindingResult result) {
        if (result.hasErrors()) {
            throw new NicknameValidationRequestException();
        }

        memberService.updateNickname(loginMember, nicknameRequest.getNickname());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/detailedPoint")
    public ResponseEntity<DetailedPointResponse> detailedPoint(LoginMember loginMember) {
        DetailedPointResponse response = memberService.detailedPoint(loginMember.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/account")
    public ResponseEntity<AccountInfoResponse> accountInfo(LoginMember loginMember) {
        return ResponseEntity.ok(memberService.accountInfo(loginMember));
    }

    @PostMapping("/me/account")
    public ResponseEntity<Void> registerAccount(LoginMember loginMember,
                                                @Valid @ModelAttribute AccountRegisterRequest accountRegisterRequest,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AccountRegisterValidationRequestException();
        }

        memberService.registerAccount(loginMember, accountRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/me/exchange")
    public ResponseEntity<Void> requestExchange(LoginMember loginMember) {
        memberService.exchange(loginMember.getId());
        return ResponseEntity.ok().build();
    }
}
