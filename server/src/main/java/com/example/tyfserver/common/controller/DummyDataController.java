package com.example.tyfserver.common.controller;

import com.example.tyfserver.auth.util.JwtTokenProvider;
import com.example.tyfserver.common.service.DummyDataService;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dummy")
@RequiredArgsConstructor
public class DummyDataController {

    private final DummyDataService dummyDataService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @GetMapping("/members")
    public void putMemberDummyData() {
        dummyDataService.putMemberDummyData();
    }

    @PostMapping("/master-token/{memberId}")
    public ResponseEntity<String> createMasterToken(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return ResponseEntity.ok(jwtTokenProvider.createMasterToken(member.getId(), member.getEmail()));
    }
}
