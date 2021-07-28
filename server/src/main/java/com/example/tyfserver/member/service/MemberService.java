package com.example.tyfserver.member.service;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.common.util.CloudFrontUrlGenerator;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.DuplicatedNicknameException;
import com.example.tyfserver.member.exception.DuplicatedPageNameException;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3Connector s3Connector;

    public void validatePageName(PageNameValidationRequest request) {
        if (memberRepository.existsByPageName(request.getPageName())) {
            throw new DuplicatedPageNameException();
        }
    }

    public void validateNickname(NicknameValidationRequest request) {
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new DuplicatedNicknameException();
        }
    }

    public MemberResponse findMember(String pageName) {
        Member member = memberRepository.findByPageName(pageName)
                .orElseThrow(MemberNotFoundException::new);
        return new MemberResponse(member);
    }

    public MemberDetailResponse findMemberDetail(Long id) {
        Member member = findMember(id);
        return new MemberDetailResponse(member);
    }

    public PointResponse findMemberPoint(Long id) {
        Member member = findMember(id);
        return new PointResponse(member.getPoint());
    }

    public List<CurationsResponse> findCurations() {
        return memberRepository.findCurations();
    }

    public ProfileResponse uploadProfile(MultipartFile multipartFile, LoginMember loginMember) {
        Member findMember = findMember(loginMember.getId());
        deleteProfile(findMember);
        String uploadedFile = s3Connector.upload(multipartFile, loginMember.getId());
        findMember.uploadProfileImage(uploadedFile);
        return new ProfileResponse(CloudFrontUrlGenerator.generateUrl(uploadedFile));
    }

    public void deleteProfile(LoginMember loginMember) {
        Member findMember = findMember(loginMember.getId());
        deleteProfile(findMember);
    }

    public void updateBio(LoginMember loginMember, String bio) {
        Member member = findMember(loginMember.getId());
        member.updateBio(bio);
    }

    public void updateNickName(LoginMember loginMember, String nickName) {
        Member member = findMember(loginMember.getId());
        member.updateNickName(nickName);
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new);
    }

    private void deleteProfile(Member member) {
        if (member.getProfileImage() == null) {
            return;
        }

        s3Connector.delete(member.getProfileImage());
        member.deleteProfile();
    }
}
