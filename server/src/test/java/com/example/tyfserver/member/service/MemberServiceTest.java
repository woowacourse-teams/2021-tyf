package com.example.tyfserver.member.service;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.common.util.Aes256Util;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.*;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.*;
import com.example.tyfserver.member.repository.AccountRepository;
import com.example.tyfserver.member.repository.ExchangeRepository;
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

    private static final long POINT = 10000L;
    private static final long INVALID_ID = -1L;
    private static final String INVALID_PAGE_NAME = "INVALID_PAGE_NAME";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private Aes256Util aes256Util;

    @MockBean
    private S3Connector s3Connector;

    private Member member = MemberTest.testMemberWithAvailablePoint(POINT);
    private Member registeredCreator = MemberTest.testMemberWithAccount(1, AccountStatus.REGISTERED);
    private Member unregisteredCreator = MemberTest.testMemberWithAccount(2, AccountStatus.UNREGISTERED);

    @BeforeEach
    void setUp() {
        member.addInitialAccount(new Account());
        initMember(member);
        initMember(registeredCreator);
        initMember(unregisteredCreator);
    }

    private Member initMember(Member member) {
        accountRepository.save(member.getAccount());
        return memberRepository.save(member);
    }

    private Donation initDonation(long point) {
        Donation donation = new Donation(new Message("후원자"), point);
        member.receiveDonation(donation);
        return donationRepository.save(donation);
    }

    @Test
    @DisplayName("validatePageName")
    public void validatePageName() {
        assertThatThrownBy(() -> memberService.validatePageName(new PageNameRequest(member.getPageName())))
                .isExactlyInstanceOf(DuplicatedPageNameException.class);
    }

    @Test
    @DisplayName("validateNickname")
    public void validateNickname() {
        assertThatThrownBy(() -> memberService.validateNickname(new NicknameRequest(member.getNickname())))
                .isExactlyInstanceOf(DuplicatedNicknameException.class);
    }

    @Test
    @DisplayName("findMemberByPageName")
    public void findMemberByPageName() {
        //given & when
        MemberResponse response = memberService.findMemberByPageName(member.getPageName());
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new MemberResponse(
                        member.getEmail(), member.getNickname(), member.getPageName(), member.getBio(),
                        member.getProfileImage(), member.getPoint(), false));

        assertThatThrownBy(() -> memberService.findMemberByPageName(INVALID_PAGE_NAME))
                .isExactlyInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findMemberById")
    public void findMemberById() {
        //given & when
        MemberResponse response = memberService.findMemberById(member.getId());
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new MemberResponse(
                        member.getEmail(), member.getNickname(), member.getPageName(), member.getBio(),
                        member.getProfileImage(), member.getPoint(), false));

        assertThatThrownBy(() -> memberService.findMemberById(INVALID_ID))
                .isExactlyInstanceOf(MemberNotFoundException.class);
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
                .isExactlyInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("findCurations")
    public void findCurationsTest() {
        List<CurationsResponse> curations = memberService.findCurations();
        assertThat(curations).hasSize(3);
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
        //given
        initDonation(1000L).toExchanged();
        initDonation(2000L);
        initDonation(3000L);

        //when
        DetailedPointResponse response = memberService.detailedPoint(member.getId());

        //then
        assertThat(response.getCurrentPoint()).isEqualTo(5000);
        assertThat(response.getExchangedTotalPoint()).isEqualTo(1000);
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
        initDonation(10000L);

        memberService.exchange(member.getId());
        List<Exchange> exchanges = exchangeRepository.findByStatusAndMember(ExchangeStatus.WAITING, member);

        assertThat(exchanges).hasSize(1);
        Exchange exchange = exchanges.get(0);
        assertThat(exchange.getExchangeAmount()).isEqualTo(0);
    }

    @Test
    @DisplayName("정산을 신청한다 - 이미 신청해놓은 경우")
    public void exchangeAlreadyRequest() {
        initDonation(10000L);

        memberService.exchange(member.getId());

        assertThatThrownBy(() -> memberService.exchange(member.getId()))
                .isExactlyInstanceOf(AlreadyRequestExchangeException.class);
    }

    @Test
    @DisplayName("정산을 신청한다 - 정산 가능한 금액이 만원 미만인 경우")
    public void exchangeLessThanLimitAmount() {
        initDonation(9999L);

        assertThatThrownBy(() -> memberService.exchange(member.getId()))
                .isExactlyInstanceOf(ExchangeAmountException.class);
    }
}
