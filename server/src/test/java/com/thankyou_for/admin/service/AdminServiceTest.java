package com.thankyou_for.admin.service;

import com.thankyou_for.admin.domain.AdminAccount;
import com.thankyou_for.admin.dto.AccountRejectRequest;
import com.thankyou_for.admin.dto.AdminLoginRequest;
import com.thankyou_for.admin.dto.ExchangeResponse;
import com.thankyou_for.admin.dto.RequestingAccountResponse;
import com.thankyou_for.admin.exception.ExchangeDoesNotAppliedException;
import com.thankyou_for.admin.exception.InvalidAdminException;
import com.thankyou_for.auth.dto.TokenResponse;
import com.thankyou_for.common.util.Aes256Util;
import com.thankyou_for.common.util.S3Connector;
import com.thankyou_for.common.util.SmtpMailConnector;
import com.thankyou_for.donation.domain.Donation;
import com.thankyou_for.donation.domain.DonationStatus;
import com.thankyou_for.donation.domain.DonationTest;
import com.thankyou_for.donation.repository.DonationRepository;
import com.thankyou_for.member.domain.*;
import com.thankyou_for.member.exception.MemberNotFoundException;
import com.thankyou_for.member.repository.AccountRepository;
import com.thankyou_for.member.repository.ExchangeRepository;
import com.thankyou_for.member.repository.MemberRepository;
import com.thankyou_for.payment.repository.PaymentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import supports.IntegrationTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@IntegrationTest
class AdminServiceTest {

    private static final YearMonth EXCHANGE_ON_2021_1 = YearMonth.of(2021, 1);
    private static final LocalDateTime DONATION_CREATED_AT_2021_1_1 = LocalDateTime.of(2021, 1, 1, 0, 0);
    private static final long DEFAULT_AMOUNT = 10000L;

    @Autowired
    private EntityManager em;

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
    @Autowired
    private ScheduledTaskHolder scheduledTaskHolder;

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
        return memberRepository.save(member);
    }

    private Donation initDonation(Member creator, LocalDateTime createdAt) {
        Donation donation = new Donation(DonationTest.testMessage(), 10000, createdAt);
        creator.receiveDonation(donation);
        return donationRepository.save(donation);
    }

    private Exchange initExchange(long amount, Member member) {
        Exchange exchange = new Exchange(amount, EXCHANGE_ON_2021_1, member);
        return exchangeRepository.save(exchange);
    }

    private Donation find(Donation donation) {
        return donationRepository.findById(donation.getId()).get();
    }

    private Member find(Member member) {
        return memberRepository.findById(member.getId()).get();
    }

    private Exchange find(Exchange exchange) {
        return exchangeRepository.findById(exchange.getId()).get();
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
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

        flushAndClear();

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
        Donation donation = initDonation(registeredMember, DONATION_CREATED_AT_2021_1_1);
        Donation donationNextMonth = initDonation(registeredMember, LocalDateTime.of(2021, 2, 1, 0, 0));
        Exchange exchange = initExchange(DEFAULT_AMOUNT, registeredMember);

        doNothing().when(mailConnector).sendExchangeApprove(anyString());

        flushAndClear();

        //when
        adminService.approveExchange(registeredMember.getPageName());

        //then
        assertThat(find(donation).getStatus()).isEqualTo(DonationStatus.EXCHANGED);
        assertThat(find(donationNextMonth).getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
        assertThat(find(exchange).getStatus()).isEqualTo(ExchangeStatus.APPROVED);
    }

    @Test
    @DisplayName("정산 승인 - 요청된 정산이 없을 경우")
    public void approveExchange_ExchangeDoesNotApplied() {
        //given
        Donation donation = initDonation(registeredMember, DONATION_CREATED_AT_2021_1_1);

        flushAndClear();

        //when
        //then
        assertThatThrownBy(() -> adminService.approveExchange(registeredMember.getPageName()))
                .isExactlyInstanceOf(ExchangeDoesNotAppliedException.class);
        assertThat(find(donation).getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
    }

    @Test
    @DisplayName("정산 승인 - 회원을 찾을 수 없는 경우")
    public void approveExchange_MemberNotFound() {
        //given
        Donation donation = initDonation(registeredMember, DONATION_CREATED_AT_2021_1_1);

        flushAndClear();

        //when
        //then
        assertThatThrownBy(() -> adminService.approveExchange("any"))
                .isExactlyInstanceOf(MemberNotFoundException.class);
        assertThat(find(donation).getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
    }

    @Test
    @DisplayName("정산 거절")
    public void rejectExchange() {
        //given
        Donation donation = initDonation(registeredMember, DONATION_CREATED_AT_2021_1_1);
        Exchange exchange = initExchange(DEFAULT_AMOUNT, registeredMember);

        doNothing().when(mailConnector).sendExchangeReject(anyString(), anyString());

        flushAndClear();

        //when
        adminService.rejectExchange(registeredMember.getPageName(), "just denied");

        //then
        assertThat(find(donation).getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
        assertThat(find(exchange).getStatus()).isEqualTo(ExchangeStatus.REJECTED);
    }

    @Test
    @DisplayName("정산 거절 - 요청된 정산이 없을 경우")
    public void rejectExchange_ExchangeDoesNotApplied() {
        //given
        Donation donation = initDonation(registeredMember, DONATION_CREATED_AT_2021_1_1);

        flushAndClear();

        //when
        //then
        assertThatThrownBy(() -> adminService.rejectExchange(registeredMember.getPageName(), "just denied"))
                .isExactlyInstanceOf(ExchangeDoesNotAppliedException.class);
        assertThat(find(donation).getStatus()).isEqualTo(DonationStatus.WAITING_FOR_EXCHANGE);
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

        flushAndClear();

        adminService.approveAccount(requestingMember.getId());

        //then
        assertThat(find(requestingMember).getAccountStatus()).isEqualTo(AccountStatus.REGISTERED);
    }

    @Test
    @DisplayName("결제 계좌요청을 반려한다. 데이터는 지우지 않음")
    public void rejectAccount() {
        //given
        doNothing().when(s3Connector).delete(anyString());
        doNothing().when(mailConnector).sendAccountApprove(requestingMember.getEmail());

        flushAndClear();

        //when
        adminService.rejectAccount(requestingMember.getId(), new AccountRejectRequest("just denied"));

        //then
        assertThat(find(requestingMember).getAccountStatus()).isEqualTo(AccountStatus.REJECTED);
    }

    @Test
    @DisplayName("계좌 승인 요청중 목록을 반환한다.")
    public void findRequestingAccounts() {
        //given
        doNothing().when(s3Connector).delete(anyString());
        doNothing().when(mailConnector).sendAccountApprove(this.registeredMember.getEmail());
        when(aes256Util.decrypt(anyString())).thenReturn("123-456-789");

        flushAndClear();

        //when
        List<RequestingAccountResponse> requestingAccounts = adminService.findRequestingAccounts();

        //then
        assertThat(requestingAccounts).hasSize(1);

        RequestingAccountResponse response = requestingAccounts.get(0);
        Member findRequestingMember = find(requestingMember);

        assertThat(response.getMemberId()).isEqualTo(findRequestingMember.getId());
        assertThat(response.getNickname()).isEqualTo(findRequestingMember.getNickname());
        assertThat(response.getPageName()).isEqualTo(findRequestingMember.getPageName());
        assertThat(response.getAccountHolder()).isEqualTo(findRequestingMember.getAccount().getAccountHolder());
        assertThat(response.getAccountNumber()).isEqualTo("123-456-789");
        assertThat(response.getBank()).isEqualTo(findRequestingMember.getAccount().getBank());
        assertThat(response.getBankbookImageUrl()).isEqualTo(findRequestingMember.getAccount().getBankbookUrl());
    }

    @Test
    @DisplayName("매달 1일 00:00에 정산금액을 계산한다.")
    public void updateExchangeAmount_scheduler() {
        String methodName = AdminService.class.getName() + ".updateExchangeAmount";
        String cron = "0 0 0 1 * *";

        long count = scheduledTaskHolder.getScheduledTasks().stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals(cron) && cronTask.toString().equals(methodName))
                .count();

        Assertions.assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("정산승인월 1일 00:00에 생성된 정산의 금액을 갱신한다.")
    void updateExchangeAmount() {
        // given
        YearMonth exchangeApproveOn = YearMonth.now();
        Member creator1 = initMember(3, AccountStatus.REGISTERED);
        Member creator2 = initMember(4, AccountStatus.REGISTERED);

        // O
        initDonation(creator1, exchangeApproveOn.minusMonths(2).atDay(1).atStartOfDay());
        initDonation(creator1, exchangeApproveOn.minusMonths(2).atDay(1).atStartOfDay());
        initDonation(creator2, exchangeApproveOn.minusMonths(1).atDay(1).atStartOfDay());
        initDonation(creator2, exchangeApproveOn.minusMonths(1).atEndOfMonth().atTime(23, 59, 59));

        // X
        Donation donation = initDonation(creator1, exchangeApproveOn.minusMonths(2).atDay(1).atStartOfDay());
        donation.toExchanged();
        initDonation(creator2, exchangeApproveOn.atDay(1).atStartOfDay());

        Exchange exchange1 = initExchange(0, creator1);
        Exchange exchange2 = initExchange(0, creator2);

        // when
        flushAndClear();
        adminService.updateExchangeAmount();
        flushAndClear();

        // then
        assertThat(find(exchange1).getExchangeAmount()).isEqualTo(20000);
        assertThat(find(exchange2).getExchangeAmount()).isEqualTo(20000);
    }
}
