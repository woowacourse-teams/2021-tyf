package com.example.tyfserver.member.service;

import com.example.tyfserver.member.dto.UrlValidationRequest;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void validateUrl(UrlValidationRequest urlValidationRequest) {
        if (memberRepository.existsByLandingPageUrl(urlValidationRequest.getUrl())) {
            throw new RuntimeException("이미 존재하는 url 입니다.");
        }
    }
}
