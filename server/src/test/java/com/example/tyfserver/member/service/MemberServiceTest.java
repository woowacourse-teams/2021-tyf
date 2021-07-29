package com.example.tyfserver.member.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.common.exception.S3FileNotFoundException;
import com.example.tyfserver.common.util.CloudFrontUrlGenerator;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.DuplicatedNicknameException;
import com.example.tyfserver.member.exception.DuplicatedPageNameException;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private CloudFrontUrlGenerator cloudFrontUrlGenerator;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private S3Connector s3Connector;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("pageNameRequest validate test")
    public void validatePageName() {
        //given
        PageNameValidationRequest request = new PageNameValidationRequest("중복됨");
        //when
        when(memberRepository.existsByPageName(request.getPageName()))
                .thenReturn(true);
        //then
        assertThatThrownBy(() -> memberService.validatePageName(request))
                .isInstanceOf(DuplicatedPageNameException.class);
    }

    @Test
    @DisplayName("nickNameRequest validate test")
    public void validateNickname() {
        //given
        NicknameRequest request = new NicknameRequest("중복됨");
        //when
        when(memberRepository.existsByNickname(request.getNickname()))
                .thenReturn(true);
        //then
        assertThatThrownBy(() -> memberService.validateNickname(request))
                .isInstanceOf(DuplicatedNicknameException.class);
    }

    @Test
    @DisplayName("findMember test")
    public void findMember() {
        //given
        //when
        when(memberRepository.findByPageName("pageName"))
                .thenReturn(
                        Optional.of(new Member("email", "nickname", "pagename", Oauth2Type.GOOGLE)));
        //then
        MemberResponse response = memberService.findMember("pageName");
        assertThat(response.getEmail()).isEqualTo("email");
        assertThat(response.getNickname()).isEqualTo("nickname");
        assertThat(response.getPageName()).isEqualTo("pagename");
    }

    @Test
    @DisplayName("findMember not found test")
    public void findMemberNotFound() {
        //given
        //when
        when(memberRepository.findByPageName("pageName"))
                .thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> memberService.findMember("pageName"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findMemberDetail test")
    public void findMemberDetailTest() {
        //given
        //when
        when(memberRepository.findById(1L))
                .thenReturn(
                        Optional.of(new Member("email", "nickname", "pagename", Oauth2Type.GOOGLE)));

        //then
        MemberDetailResponse response = memberService.findMemberDetail(1L);
        assertThat(response.getEmail()).isEqualTo("email");
        assertThat(response.getNickname()).isEqualTo("nickname");
        assertThat(response.getPageName()).isEqualTo("pagename");
    }

    @Test
    @DisplayName("findMemberDetail not found test")
    public void findMemberDetailNotFoundTest() {
        //given
        //when
        when(memberRepository.findById(1L))
                .thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> memberService.findMemberDetail(1L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findMemberPoint test")
    public void findMemberPointTest() {
        //given
        //when
        when(memberRepository.findById(1L))
                .thenReturn(
                        Optional.of(new Member("email", "nickname", "pagename", Oauth2Type.GOOGLE)));

        //then
        PointResponse response = memberService.findMemberPoint(1L);
        assertThat(response.getPoint()).isEqualTo(0L);
    }

    @Test
    @DisplayName("findMemberPoint not found test")
    public void findMemberPointNotFoundTest() {
        //given
        //when
        when(memberRepository.findById(1L))
                .thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> memberService.findMemberPoint(1L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findCurations test")
    public void findCurationsTest() {
        //given
        when(memberRepository.findCurations())
                .thenReturn(
                        Collections.singletonList(
                                new CurationsResponse("nickname", 100L, "pageName")));
        //when
        CurationsResponse response = memberService.findCurations().get(0);
        //then
        assertThat(response.getNickname()).isEqualTo("nickname");
        assertThat(response.getDonationAmount()).isEqualTo(100L);
        assertThat(response.getPageName()).isEqualTo("pageName");
    }

    @Test
    @DisplayName("deleteProfile throw exception test")
    public void deleteProfileTestFileNotFoundException() {
        //when
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(MemberTest.testMemberWithProfileImage()));

        doThrow(new S3FileNotFoundException()).when(s3Connector).delete(Mockito.anyString());
        //then
        assertThatThrownBy(() -> memberService.deleteProfile(new LoginMember(1L, "email")))
                .isInstanceOf(S3FileNotFoundException.class);
    }

    @Test
    @DisplayName("updateBio test")
    void updateBioTest() {
        //given
        LoginMember loginMember = new LoginMember(1L, "test@email.com");
        String expectedBio = "안녕하세요! 로키입니다.";
        Member givenMember = new Member("test@email.com", "로키", "roki", Oauth2Type.NAVER);
        when(memberRepository.findById(loginMember.getId()))
                .thenReturn(Optional.of(givenMember));

        //when
        memberService.updateBio(loginMember, expectedBio);

        //then
        assertThat(givenMember.getBio()).isEqualTo(expectedBio);
    }

    @Test
    @DisplayName("updateNickName test")
    void updateNickNameTest() {
        //given
        LoginMember loginMember = new LoginMember(1L, "test@email.com");
        String expectedNickName = "로키";
        Member givenMember = new Member("test@email.com", "로키", "roki", Oauth2Type.NAVER);
        when(memberRepository.findById(loginMember.getId()))
                .thenReturn(Optional.of(givenMember));

        //when
        memberService.updateNickName(loginMember, expectedNickName);

        //then
        assertThat(givenMember.getNickname()).isEqualTo(expectedNickName);
    }
}
