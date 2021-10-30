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
import com.example.tyfserver.payment.domain.PaymentServiceConnector;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import supports.IntegrationTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@IntegrationTest
class MemberServiceTest {

    private static final long INVALID_ID = -1L;

    @Autowired
    private EntityManager em;

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
    @MockBean
    private PaymentServiceConnector paymentServiceConnector;

    private Member member = MemberTest.testMemberWithAvailablePoint(10000L);
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

    private Donation initDonation(long point, Member creator) {
        Donation donation = new Donation(new Message("후원자"), point);
        creator.receiveDonation(donation);
        return donationRepository.save(donation);
    }

    private Member find(Member member) {
        return memberRepository.findById(member.getId()).get();
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
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

        assertThatThrownBy(() -> memberService.findMemberByPageName("INVALID_PAGE_NAME"))
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
        assertAll(
                () -> assertThat(curations).hasSize(1),
                () -> assertThat(curations.get(0)).usingRecursiveComparison().isEqualTo(
                        new CurationsResponse(registeredCreator.getNickname(), registeredCreator.getPageName(),
                                registeredCreator.getProfileImage(), registeredCreator.getBio()))
        );
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
        initDonation(1000L, registeredCreator).toExchanged();
        initDonation(2000L, registeredCreator);
        initDonation(3000L, registeredCreator);
        flushAndClear();

        //when
        DetailedPointResponse response = memberService.detailedPoint(registeredCreator.getId());

        //then
        assertThat(response.getCurrentPoint()).isEqualTo(5000);
        assertThat(response.getExchangedTotalPoint()).isEqualTo(1000);
    }

    @Test
    @Disabled
    @DisplayName("계좌정보를 등록한다. 계좌정보는 암호화되어 적용된다.")
    public void registerAccountInfo() {
        //given
        LoginMember loginMember = new LoginMember(member.getId(), member.getEmail());
        final AccountRegisterRequest request = new AccountRegisterRequest("test",
                "1234-5678-1234", "900101-1000000", "081");
        flushAndClear();

        //when
        // todo 통합테스트 -> mock 테스트 전환필요
//        when(paymentServiceConnector.requestHolderNameOfAccount("081", "1234-5678-1234")).thenReturn();
        memberService.registerAccount(loginMember, request);

        //then
        Account account = find(member).getAccount();
        assertThat(account.getStatus()).isEqualTo(AccountStatus.REQUESTING);
        assertThat(account.getAccountHolder()).isEqualTo(request.getAccountHolder());
        assertThat(account.getBank()).isEqualTo(request.getBank());
        assertThat(account.getAccountNumber()).isEqualTo(aes256Util.encrypt(request.getAccountNumber()));
        assertThat(account.getResidentRegistrationNumber()).isEqualTo(aes256Util.encrypt(request.getResidentRegistrationNumber()));
    }

    @Test
    @DisplayName("정산을 신청한다")
    public void exchange() {
        //given
        initDonation(10000L, registeredCreator);
        flushAndClear();

        //when
        memberService.exchange(registeredCreator.getId());

        //then
        List<Exchange> exchanges = exchangeRepository.findByStatusAndMember(ExchangeStatus.WAITING, registeredCreator);

        assertThat(exchanges).hasSize(1);
        Exchange exchange = exchanges.get(0);
        assertThat(exchange.getExchangeAmount()).isEqualTo(0);
    }

    @Test
    @DisplayName("정산을 신청한다 - 이미 신청해놓은 경우")
    public void exchangeAlreadyRequest() {
        //given
        initDonation(10000L, registeredCreator);
        memberService.exchange(registeredCreator.getId());
        flushAndClear();

        //when
        //then
        assertThatThrownBy(() -> memberService.exchange(registeredCreator.getId()))
                .isExactlyInstanceOf(AlreadyRequestExchangeException.class);
    }

    @Test
    @DisplayName("정산을 신청한다 - 정산 가능한 금액이 만원 미만인 경우")
    public void exchangeLessThanLimitAmount() {
        //given
        initDonation(9999L, registeredCreator);
        flushAndClear();

        //when
        //then
        assertThatThrownBy(() -> memberService.exchange(registeredCreator.getId()))
                .isExactlyInstanceOf(ExchangeAmountException.class);
    }
}
