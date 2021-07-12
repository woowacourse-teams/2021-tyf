package com.example.tyfserver.member.service;

import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.MemberResponse;
import com.example.tyfserver.member.dto.NicknameValidationRequest;
import com.example.tyfserver.member.dto.PointResponse;
import com.example.tyfserver.member.dto.UrlValidationRequest;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void validateUrl(UrlValidationRequest request) {
        if (memberRepository.existsByUrlName(request.getUrlName())) {
            throw new RuntimeException("이미 존재하는 url 입니다.");
        }
    }

    public void validateNickname(NicknameValidationRequest request) {
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임 입니다.");
        }
    }

    public MemberResponse findMember(String urlName) {
        Member findMember = memberRepository.findByUrlName(urlName)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
        return new MemberResponse(findMember);
    }

    public PointResponse findMemberPoint(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
        return new PointResponse(findMember.getPoint().getPoint());
    }
}
