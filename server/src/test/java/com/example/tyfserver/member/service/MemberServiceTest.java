package com.example.tyfserver.member.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.common.util.Aes256Util;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.AccountStatus;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.Point;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.*;
import com.example.tyfserver.member.repository.AccountRepository;
import com.example.tyfserver.member.repository.ExchangeRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.repository.PaymentRepository;
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

    private static final String EMAIL = "email";
    private static final String NICKNAME = "nickname";
    private static final String PAGENAME = "pagename";
    private static final String 제_페이지에_와주셔서_감사합니다 = "제 페이지에 와주셔서 감사합니다!";
    private static final String PROFILE = "profile";
    private static final long POINT = 10000L;
    private static final String DONATOR_NAME = "후원자";
    private static final long INVALID_ID = -1L;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private Aes256Util aes256Util;

    @MockBean
    private S3Connector s3Connector;

    private Member member = new Member(EMAIL, NICKNAME, PAGENAME, Oauth2Type.GOOGLE, PROFILE, new Point(POINT));

    @BeforeEach
    void setUp() {
        Member savedMember = memberRepository.save(member);
        savedMember.addInitialAccount(accountRepository.save(new Account()));
    }

    @Test
    @DisplayName("validatePageName")
    public void validatePageNameTest() {
        assertThatThrownBy(() ->
                memberService.validatePageName(new PageNameRequest(PAGENAME))
        ).isInstanceOf(DuplicatedPageNameException.class);
    }

    @Test
    @DisplayName("validateNickname")
    public void validateNicknameTest() {
        assertThatThrownBy(() ->
                memberService.validateNickname(new NicknameRequest(NICKNAME))
        ).isInstanceOf(DuplicatedNicknameException.class);
    }

    @Test
    @DisplayName("findMember")
    public void findMemberTest() {
        //given & when
        MemberResponse response = memberService.findMemberByPageName(PAGENAME);
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new MemberResponse(EMAIL, NICKNAME, PAGENAME, 제_페이지에_와주셔서_감사합니다, PROFILE, POINT, false));

        assertThatThrownBy(() -> memberService.findMemberByPageName("asdf"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findMemberDetail")
    public void findMemberDetailTest() {
        //given & when
        MemberResponse response = memberService.findMemberById(member.getId());
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new MemberResponse(EMAIL, NICKNAME, PAGENAME, 제_페이지에_와주셔서_감사합니다, PROFILE, POINT, false));

        assertThatThrownBy(() -> memberService.findMemberById(INVALID_ID))
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

        assertThatThrownBy(() -> memberService.findMemberPoint(INVALID_ID))
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
        when(s3Connector.uploadProfile(file, loginMember.getId())).thenReturn(uploadedImage);

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
    @DisplayName("detailedPoint")
    public void detailedPointTest() {
        //given & when
        Donation donation1 = initDonation(1000L);
        Donation donation2 = initDonation(2000L);
        Donation donation3 = initDonation(3000L);
        Donation donation4 = initDonation(4000L);

        donation1.toExchanged();
        donation4.toCancelled();

        //then
        DetailedPointResponse response = memberService.detailedPoint(member.getId());
        assertThat(response.getCurrentPoint()).isEqualTo(5000);
        assertThat(response.getExchangeablePoint()).isEqualTo(0L);
        assertThat(response.getExchangedTotalPoint()).isEqualTo(1000);
    }

    private Donation initDonation(long point) {
        Donation donation = new Donation(new Message(DONATOR_NAME), point);
        member.addGivenDonation(donation);
        donationRepository.save(donation);
        return donation;
    }

    @Test
    @DisplayName("계좌정보를 등록한다. 계좌정보는 암호화되어 적용된다.")
    public void registerAccountInfo() {
        //given
        LoginMember loginMember = new LoginMember(member.getId(), member.getEmail());
        final AccountRegisterRequest test = new AccountRegisterRequest("test",
                "1234-5678-1234", null, "하나");

        //when
        memberService.registerAccount(loginMember, test);


        //then
        Account account = member.getAccount();
        assertThat(account.getStatus()).isEqualTo(AccountStatus.REQUESTING);
        assertThat(account.getAccountHolder()).isEqualTo(test.getAccountHolder());
        assertThat(account.getBank()).isEqualTo(test.getBank());
        assertThat(account.getAccountNumber()).isEqualTo(aes256Util.encrypt(test.getAccountNumber()));
    }

    @Test
    @DisplayName("정산을 신청한다")
    public void exchange() {
        Donation donation = initDonation(20000L);
        donation.toExchangeable();

        memberService.exchange(member.getId());
        boolean result = exchangeRepository.existsByPageName(member.getPageName());

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("정산을 신청한다 - 이미 신청해놓은 경우")
    public void exchangeAlreadyRequest() {
        Donation donation = initDonation(11000L);
        donation.toExchangeable();

        memberService.exchange(member.getId());

        assertThatThrownBy(() -> memberService.exchange(member.getId()))
                .isInstanceOf(AlreadyRequestExchangeException.class);
    }

    @Test
    @DisplayName("정산을 신청한다 - 정산 가능한 금액이 만원 이하인 경우")
    public void exchangeLessThanLimitAmount() {
        Donation donation1 = initDonation(10000L);
        Donation donation2 = initDonation(11000L);
        donation1.toExchangeable();

        assertThatThrownBy(() -> memberService.exchange(member.getId()))
                .isInstanceOf(ExchangeAmountException.class);
    }
}
