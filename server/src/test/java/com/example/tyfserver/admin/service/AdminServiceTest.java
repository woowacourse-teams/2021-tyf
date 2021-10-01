package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.domain.AdminAccount;
import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.admin.exception.InvalidAdminException;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.common.util.Aes256Util;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.donation.domain.Message;
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

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AdminServiceTest {

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
    private DonationRepository mockedDonationRepository;
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

    private Member initMember(Member member) {
        return member;
    }

    private Donation initDonation(Long point) {
        Donation donation = new Donation(new Message("name", "message", false), point);
        registeredMember.addDonation(donation);
        donationRepository.save(donation);
        return donation;
    }

    private Exchange initExchange(long amount, Member member) {
        Exchange exchange = new Exchange(amount, YearMonth.of(2021, 1), member);
        exchangeRepository.save(exchange);
        return exchange;
    }

    @Test
    @DisplayName("정산 목록 조회")
    public void exchangeList() {
        Member member4 = initMember(4, AccountStatus.REGISTERED);
        Member member5 = initMember(5, AccountStatus.REGISTERED);
        Exchange exchange1 = initExchange(12000L, registeredMember);
        Exchange exchange2 = initExchange(21000L, member4);
        Exchange exchange3 = initExchange(31000L, member5);

        when(aes256Util.decrypt(anyString())).thenReturn("123-456-789");

        List<ExchangeResponse> responses = adminService.exchangeList();

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
        Donation donation = initDonation(13000L);
        Exchange exchange = initExchange(13000L, registeredMember);

        //when
        when(mockedDonationRepository.findDonationsToExchange(any(Member.class), any(YearMonth.class)))
                .thenReturn(Collections.singletonList(donation));
        doNothing().when(mailConnector).sendExchangeApprove(anyString());
        adminService.approveExchange(registeredMember.getPageName());

        //then
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.EXCHANGED);
        assertThat(exchange.getStatus()).isEqualTo(ExchangeStatus.APPROVED);
    }

    @Test
    @DisplayName("정산 승인 - 회원을 찾을 수 없는 경우")
    public void approveExchangeMemberNotFound() {
        assertThatThrownBy(() -> adminService.approveExchange("any"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("정산 거절")
    public void rejectExchange() {
        //given
        Donation donation = initDonation(13000L);
        Exchange exchange = initExchange(13000L, registeredMember);

        //when
        doNothing().when(mailConnector).sendExchangeReject(anyString(), anyString());
        adminService.rejectExchange(registeredMember.getPageName(), "just denied");

        //then
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
        assertThat(exchange.getStatus()).isEqualTo(ExchangeStatus.REJECTED);
    }

    @Test
    @DisplayName("정산 거절 - 회원을 찾을 수 없는 경우")
    public void rejectExchangeMemberNotFound() {
        assertThatThrownBy(() -> adminService.rejectExchange("any", "just denied"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("유효하지 않은 관리자 계정으로 로그인 요청을 한 경우")
    @Test
    void adminLogin() {
        //given
        doNothing().when(adminAccount).validateLogin(anyString(), anyString());

        //when
        TokenResponse tokenResponse = adminService.login(new AdminLoginRequest("tyf-id", "tyf-password"));

        //then
        assertThat(tokenResponse).isNotNull();
    }

    @DisplayName("유효하지 않은 관리자 계정으로 로그인 요청을 한 경우")
    @Test
    void adminLoginInvalidAdminAccount() {
        //given
        doThrow(InvalidAdminException.class)
                .when(adminAccount).validateLogin(anyString(), anyString());

        //when //then
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
    public void cancelAccount() {
        //given
        //when
        doNothing().when(s3Connector).delete(anyString());
        doNothing().when(mailConnector).sendAccountApprove(requestingMember.getEmail());
        adminService.rejectAccount(requestingMember.getId(), new AccountRejectRequest("just denied"));

        //then
        assertThat(requestingMember.getAccountStatus()).isEqualTo(AccountStatus.REJECTED);
    }

    @Test
    @DisplayName("계좌 승인 요청중 목록을 반환한다.")
    public void requestingAccounts() {
        //given
        //when
        doNothing().when(s3Connector).delete(anyString());
        doNothing().when(mailConnector).sendAccountApprove(this.registeredMember.getEmail());
        when(aes256Util.decrypt(anyString())).thenReturn("123-456-789");

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
