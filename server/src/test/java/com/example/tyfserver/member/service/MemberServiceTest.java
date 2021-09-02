package com.example.tyfserver.member.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.common.exception.S3FileNotFoundException;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.AccountStatus;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.*;
import com.example.tyfserver.member.repository.AccountRepository;
import com.example.tyfserver.member.repository.ExchangeRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.repository.PaymentRepository;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

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

    @MockBean
    private S3Connector s3Connector;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        Member savedMember = memberRepository.save(member);
        savedMember.addInitialAccount(accountRepository.save(new Account()));
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
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("email", "nickname", "pagename", "제 페이지에 와주셔서 감사합니다!", "profile", false));

        assertThatThrownBy(() -> memberService.findMember("asdf"))
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
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("email", "nickname", "pagename", "제 페이지에 와주셔서 감사합니다!", "profile", false));

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
                                new CurationsResponse("nickname", 100L,
                                        "pageName", "https://cloudfront.net/profile1.png", "I am test")));
        //when
        CurationsResponse response = memberService.findCurations().get(0);
        //then
        assertThat(response.getNickname()).isEqualTo("nickname");
        assertThat(response.getDonationAmount()).isEqualTo(100L);
        assertThat(response.getPageName()).isEqualTo("pageName");
        assertThat(response.getProfileImage()).isEqualTo("https://cloudfront.net/profile1.png");
        assertThat(response.getBio()).isEqualTo("I am test");
    }

    @Test
    @DisplayName("deleteProfile throw exception test")
    public void deleteProfileTestFileNotFoundException() {
        //when
        doNothing().when(s3Connector).delete(Mockito.anyString());
        when(s3Connector.uploadProfile(file, loginMember.getId())).thenReturn(uploadedImage);

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
        Member givenMember = MemberTest.testMember();
        when(memberRepository.findById(loginMember.getId()))
                .thenReturn(Optional.of(givenMember));

        //when
        memberService.updateNickName(loginMember, expectedNickName);

        //then
        assertThat(givenMember.getNickname()).isEqualTo(expectedNickName);
    }

    @Test
    @DisplayName("detailedPoint")
    public void detailedPointTest() {
        //given & when
        Payment payment1 = paymentRepository.save(new Payment(1000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Payment payment2 = paymentRepository.save(new Payment(2000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Payment payment3 = paymentRepository.save(new Payment(3000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Payment payment4 = paymentRepository.save(new Payment(4000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Donation donation1 = new Donation(payment1);
        Donation donation2 = new Donation(payment2);
        Donation donation3 = new Donation(payment3);
        Donation donation4 = new Donation(payment4);
        member.addDonation(donation1);
        member.addDonation(donation2);
        member.addDonation(donation3);
        member.addDonation(donation4);
        donation1.toExchanged();
        donation4.toCancelled();

        donationRepository.save(donation1);
        donationRepository.save(donation2);
        donationRepository.save(donation3);
        donationRepository.save(donation4);

        //then
        DetailedPointResponse response = memberService.detailedPoint(member.getId());
        assertThat(response.getCurrentPoint()).isEqualTo(5000);
        assertThat(response.getExchangeablePoint()).isEqualTo(0L);
        assertThat(response.getExchangedTotalPoint()).isEqualTo(1000);
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

    @Test
    @DisplayName("정산을 신청한다")
    public void exchange() {
        Payment payment = paymentRepository.save(new Payment(20000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Donation donation = new Donation(payment);
        member.addDonation(donation);
        donation.toExchangeable();
        donationRepository.save(donation);

        memberService.exchange(member.getId());
        boolean result = exchangeRepository.existsByPageName(member.getPageName());

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("정산을 신청한다 - 이미 신청해놓은 경우")
    public void exchangeAlreadyRequest() {
        Payment payment = paymentRepository.save(new Payment(11000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Donation donation = new Donation(payment);
        member.addDonation(donation);
        donation.toExchangeable();
        donationRepository.save(donation);

        memberService.exchange(member.getId());

        assertThatThrownBy(() -> memberService.exchange(member.getId()))
                .isInstanceOf(AlreadyRequestExchangeException.class);
    }

    @Test
    @DisplayName("정산을 신청한다 - 정산 가능한 금액이 만원 이하인 경우")
    public void exchangeLessThanLimitAmount() {
        Payment payment1 = paymentRepository.save(new Payment(10000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Payment payment2 = paymentRepository.save(new Payment(11000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Donation donation1 = new Donation(payment1);
        Donation donation2 = new Donation(payment2);
        member.addDonation(donation1);
        member.addDonation(donation2);
        donation1.toExchangeable();

        donationRepository.save(donation1);
        donationRepository.save(donation2);

        assertThatThrownBy(() -> memberService.exchange(member.getId()))
                .isInstanceOf(ExchangeAmountException.class);
    }
}
