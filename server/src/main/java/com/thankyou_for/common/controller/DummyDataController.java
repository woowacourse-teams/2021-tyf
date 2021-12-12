package com.thankyou_for.common.controller;

import com.thankyou_for.auth.util.JwtTokenProvider;
import com.thankyou_for.common.service.DummyDataService;
import com.thankyou_for.member.domain.Member;
import com.thankyou_for.member.exception.MemberNotFoundException;
import com.thankyou_for.member.repository.MemberRepository;
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

    @GetMapping("/payments")
    public void putPaymentAndRefundFailureDummyData() {
        dummyDataService.putPaymentAndRefundFailiureDummyData();
    }

    @GetMapping("/donations")
    public void putDonationDummyData() {
        dummyDataService.putDonationDummyData();
    }

    @PostMapping("/master-token/{memberId}")
    public ResponseEntity<String> createMasterToken(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return ResponseEntity.ok(jwtTokenProvider.createMasterToken(member.getId(), member.getEmail()));
    }
}
