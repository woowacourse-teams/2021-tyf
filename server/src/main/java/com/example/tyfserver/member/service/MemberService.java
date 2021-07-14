package com.example.tyfserver.member.service;

import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.MemberResponse;
import com.example.tyfserver.member.dto.NicknameValidationRequest;
import com.example.tyfserver.member.dto.PointResponse;
import com.example.tyfserver.member.dto.PageNameValidationRequest;
import com.example.tyfserver.member.exception.DuplicatedPageNameException;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void validatePageName(PageNameValidationRequest request) {
        if (memberRepository.existsByPageName(request.getPageName())) {
            throw new DuplicatedPageNameException();
        }
    }

    public void validateNickname(NicknameValidationRequest request) {
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임 입니다.");
        }
    }

    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
    }

    public MemberResponse findMember(String pageName) {
        Member findMember = memberRepository.findByPageName(pageName)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
        return new MemberResponse(findMember);
    }

    public PointResponse findMemberPoint(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
        return new PointResponse(findMember.getPoint().getPoint());
    }
}
