package com.example.tyfserver.member.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.AccountStatus;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.DuplicatedNicknameException;
import com.example.tyfserver.member.exception.DuplicatedPageNameException;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.AccountRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @MockBean
    private S3Connector s3Connector;

    private Member member = new Member("email", "nickname", "pagename", Oauth2Type.GOOGLE, "profile");

    @BeforeEach
    void setUp() {
        Member savedMember = memberRepository.save(member);
        savedMember.addInitialAccount(accountRepository.save(new Account()));
    }

    @Test
    @DisplayName("validatePageName")
    public void validatePageNameTest() {
        assertThatThrownBy(() ->
                memberService.validatePageName(new PageNameRequest("pagename"))
        ).isInstanceOf(DuplicatedPageNameException.class);
    }

    @Test
    @DisplayName("validateNickname")
    public void validateNicknameTest() {
        assertThatThrownBy(() ->
                memberService.validateNickname(new NicknameRequest("nickname"))
        ).isInstanceOf(DuplicatedNicknameException.class);
    }

    @Test
    @DisplayName("findMember")
    public void findMemberTest() {
        //given & when
        MemberResponse response = memberService.findMember("pagename");
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("email", "nickname", "pagename", "제 페이지에 와주셔서 감사합니다!", "profile"));

        assertThatThrownBy(() -> memberService.findMember("asdf"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findMemberDetail")
    public void findMemberDetailTest() {
        //given & when
        MemberResponse response = memberService.findMemberDetail(member.getId());
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("email", "nickname", "pagename", "제 페이지에 와주셔서 감사합니다!", "profile"));

        assertThatThrownBy(() -> memberService.findMemberDetail(1000L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findMemberPoint")
    public void findMemberPointTest() {
        //given & when
        PointResponse response = memberService.findMemberPoint(member.getId());
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new PointResponse(0L));

        assertThatThrownBy(() -> memberService.findMemberPoint(1000L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findCurations")
    public void findCurationsTest() {
        List<CurationsResponse> curations = memberService.findCurations();
        assertThat(curations).hasSize(1);
    }

    @Test
    @DisplayName("uploadProfile")
    public void uploadProfileTest() {
        //given
        MockMultipartFile file = new MockMultipartFile("profileImage", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());
        LoginMember loginMember = new LoginMember(member.getId(), member.getEmail());
        String uploadedImage = "uploadedImage";

        //when
        doNothing().when(s3Connector).delete(Mockito.anyString());
        when(s3Connector.upload(file, loginMember.getId())).thenReturn(uploadedImage);

        //then
        assertThat(memberService.uploadProfile(file, loginMember)).usingRecursiveComparison()
                .isEqualTo(new ProfileResponse(uploadedImage));
        assertThat(member.getProfileImage()).isEqualTo(uploadedImage);
    }

    @Test
    @DisplayName("deleteProfile")
    public void deleteProfileTest() {
        //given
        LoginMember loginMember = new LoginMember(member.getId(), member.getEmail());

        //when
        doNothing().when(s3Connector).delete(Mockito.anyString());
        memberService.deleteProfile(loginMember);

        //then
        assertThat(member.getProfileImage()).isNull();
    }

    @Test
    @DisplayName("updateBio")
    public void updateBioTest() {
        //given
        LoginMember loginMember = new LoginMember(member.getId(), member.getEmail());

        //when
        memberService.updateBio(loginMember, "updatedBio");

        //then
        assertThat(member.getBio()).isEqualTo("updatedBio");
    }

    @Test
    @DisplayName("계좌정보를 등록한다.")
    public void registerAccountInfo() {
        //given
        LoginMember loginMember = new LoginMember(member.getId(), member.getEmail());
        final AccountRegisterRequest test = new AccountRegisterRequest("test", "1234-5678-1234", null, "하나");

        //when
        memberService.registerAccount(loginMember, test);

        //then
        Account account = member.getAccount();
        assertThat(account.getStatus()).isEqualTo(AccountStatus.REQUESTING);
        assertThat(account.getAccountHolder()).isEqualTo("test");
        assertThat(account.getBank()).isEqualTo("하나");
        assertThat(account.getAccountNumber()).isEqualTo("1234-5678-1234");
    }

}
