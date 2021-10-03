package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.domain.AdminAccount;
import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.admin.exception.ExchangeDoesNotAppliedException;
import com.example.tyfserver.admin.exception.InvalidAdminException;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.common.util.Aes256Util;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.donation.domain.DonationTest;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.*;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.AccountRepository;
import com.example.tyfserver.member.repository.ExchangeRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AdminServiceTest {

    private static final YearMonth EXCHANGE_ON_2021_1 = YearMonth.of(2021, 1);
    private static final LocalDateTime DONATION_CREATED_AT_2021_1_1 = LocalDateTime.of(2021, 1, 1, 0, 0);
    private static final long DEFAULT_AMOUNT = 10000L;

    @Autowired
    private AdminService adminService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private DonationRepository donationRepository;

    @MockBean
    private S3Connector s3Connector;
    @MockBean
    private SmtpMailConnector mailConnector;
    @MockBean
    private AdminAccount adminAccount;
    @MockBean
    private Aes256Util aes256Util;

    private Member registeredMember;
    private Member requestingMember;

    @BeforeEach
    void setUp() {
        registeredMember = initMember(1, AccountStatus.REGISTERED);
        requestingMember = initMember(2, AccountStatus.REQUESTING);
    }

    private Member initMember(int i, AccountStatus accountStatus) {
        Member member = MemberTest.testMemberWithAccount(i, accountStatus);
        accountRepository.save(member.getAccount());
        memberRepository.save(member);
        return member;
    }

    private Donation initDonation(LocalDateTime createdAt) {
        Donation donation = new Donation(DonationTest.testMessage(), 10000, createdAt);
        registeredMember.receiveDonation(donation);
        return donationRepository.save(donation);
    }

    private Exchange initExchange(long amount, Member member) {
        Exchange exchange = new Exchange(amount, EXCHANGE_ON_2021_1, member);
        return exchangeRepository.save(exchange);
    }

    @Test
    @DisplayName("정산 목록 조회")
    public void exchangeList() {
        //given
        Member member4 = initMember(4, AccountStatus.REGISTERED);
        Member member5 = initMember(5, AccountStatus.REGISTERED);
        Exchange exchange1 = initExchange(11000L, registeredMember);
        Exchange exchange2 = initExchange(12000L, member4);
        Exchange exchange3 = initExchange(13000L, member5);

        when(aes256Util.decrypt(anyString())).thenReturn("123-456-789");

        //when
        List<ExchangeResponse> responses = adminService.exchangeList();

        //then
        assertThat(responses).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new ExchangeResponse(exchange1, "123-456-789"),
                        new ExchangeResponse(exchange2, "123-456-789"),
                        new ExchangeResponse(exchange3, "123-456-789")
                );
    }

    @Test
    @DisplayName("정산 승인")
    public void approveExchange() {
        //given
        Donation donation = initDonation(DONATION_CREATED_AT_2021_1_1);
        Donation donationNextMonth = initDonation(LocalDateTime.of(2021, 2, 1, 0, 0));
        Exchange exchange = initExchange(DEFAULT_AMOUNT, registeredMember);

        doNothing().when(mailConnector).sendExchangeApprove(anyString());

        //when
        adminService.approveExchange(registeredMember.getPageName());

        //then
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.EXCHANGED);
        assertThat(donationNextMonth.getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
        assertThat(exchange.getStatus()).isEqualTo(ExchangeStatus.APPROVED);
    }

    @Test
    @DisplayName("정산 승인 - 요청된 정산이 없을 경우")
    public void approveExchange_ExchangeDoesNotApplied() {
        //given
        Donation donation = initDonation(DONATION_CREATED_AT_2021_1_1);

        //when
        //then
        assertThatThrownBy(() -> adminService.approveExchange(registeredMember.getPageName()))
                .isExactlyInstanceOf(ExchangeDoesNotAppliedException.class);
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
    }

    @Test
    @DisplayName("정산 승인 - 회원을 찾을 수 없는 경우")
    public void approveExchange_MemberNotFound() {
        //given
        Donation donation = initDonation(DONATION_CREATED_AT_2021_1_1);

        //when
        //then
        assertThatThrownBy(() -> adminService.approveExchange("any"))
                .isExactlyInstanceOf(MemberNotFoundException.class);
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
    }

    @Test
    @DisplayName("정산 거절")
    public void rejectExchange() {
        //given
        Donation donation = initDonation(DONATION_CREATED_AT_2021_1_1);
        Exchange exchange = initExchange(DEFAULT_AMOUNT, registeredMember);

        doNothing().when(mailConnector).sendExchangeReject(anyString(), anyString());

        //when
        adminService.rejectExchange(registeredMember.getPageName(), "just denied");

        //then
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
        assertThat(exchange.getStatus()).isEqualTo(ExchangeStatus.REJECTED);
    }

    @Test
    @DisplayName("정산 거절 - 요청된 정산이 없을 경우")
    public void rejectExchange_ExchangeDoesNotApplied() {
        //given
        Donation donation = initDonation(DONATION_CREATED_AT_2021_1_1);

        //when
        //then
        assertThatThrownBy(() -> adminService.rejectExchange(registeredMember.getPageName(), "just denied"))
                .isExactlyInstanceOf(ExchangeDoesNotAppliedException.class);
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
    }

    @Test
    @DisplayName("정산 거절 - 회원을 찾을 수 없는 경우")
    public void rejectExchangeMemberNotFound() {
        assertThatThrownBy(() -> adminService.rejectExchange("any", "just denied"))
                .isExactlyInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("관리자 계정으로 로그인")
    void adminLogin() {
        //given
        doNothing().when(adminAccount).validateLogin(anyString(), anyString());

        //when
        TokenResponse tokenResponse = adminService.login(new AdminLoginRequest("tyf-id", "tyf-password"));

        //then
        assertThat(tokenResponse).isNotNull();
    }

    @Test
    @DisplayName("유효하지 않은 관리자 계정으로 로그인 요청을 한 경우")
    void adminLogin_InvalidAdminAccount() {
        //given
        doThrow(InvalidAdminException.class)
                .when(adminAccount).validateLogin(anyString(), anyString());

        //when
        //then
        assertThatThrownBy(() -> adminService.login(new AdminLoginRequest("tyf-id", "tyf-password")))
                .isExactlyInstanceOf(InvalidAdminException.class);
    }

    @Test
    @DisplayName("결제 계좌요청을 승인한다.")
    public void approveAccount() {
        //given
        //when
        doNothing().when(s3Connector).delete(anyString());
        doNothing().when(mailConnector).sendAccountApprove(requestingMember.getEmail());

        adminService.approveAccount(requestingMember.getId());

        //then
        assertThat(requestingMember.getAccountStatus()).isEqualTo(AccountStatus.REGISTERED);
    }

    @Test
    @DisplayName("결제 계좌요청을 반려한다. 데이터는 지우지 않음")
    public void rejectAccount() {
        //given
        doNothing().when(s3Connector).delete(anyString());
        doNothing().when(mailConnector).sendAccountApprove(requestingMember.getEmail());

        //when
        adminService.rejectAccount(requestingMember.getId(), new AccountRejectRequest("just denied"));

        //then
        assertThat(requestingMember.getAccountStatus()).isEqualTo(AccountStatus.REJECTED);
    }

    @Test
    @DisplayName("계좌 승인 요청중 목록을 반환한다.")
    public void findRequestingAccounts() {
        //given
        doNothing().when(s3Connector).delete(anyString());
        doNothing().when(mailConnector).sendAccountApprove(this.registeredMember.getEmail());
        when(aes256Util.decrypt(anyString())).thenReturn("123-456-789");

        //when
        List<RequestingAccountResponse> requestingAccounts = adminService.findRequestingAccounts();

        //then
        assertThat(requestingAccounts).hasSize(1);

        RequestingAccountResponse response = requestingAccounts.get(0);
        assertThat(response.getMemberId()).isEqualTo(requestingMember.getId());
        assertThat(response.getNickname()).isEqualTo(requestingMember.getNickname());
        assertThat(response.getPageName()).isEqualTo(requestingMember.getPageName());
        assertThat(response.getAccountHolder()).isEqualTo(requestingMember.getAccount().getAccountHolder());
        assertThat(response.getAccountNumber()).isEqualTo("123-456-789");
        assertThat(response.getBank()).isEqualTo(requestingMember.getAccount().getBank());
        assertThat(response.getBankbookImageUrl()).isEqualTo(requestingMember.getAccount().getBankbookUrl());
    }
}
