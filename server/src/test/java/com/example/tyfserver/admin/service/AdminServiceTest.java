//package com.example.tyfserver.admin.service;
//
//import com.example.tyfserver.admin.domain.AdminAccount;
//import com.example.tyfserver.admin.dto.AccountRejectRequest;
//import com.example.tyfserver.admin.dto.AdminLoginRequest;
//import com.example.tyfserver.admin.dto.ExchangeResponse;
//import com.example.tyfserver.admin.dto.RequestingAccountResponse;
//import com.example.tyfserver.admin.exception.InvalidAdminException;
//import com.example.tyfserver.auth.domain.Oauth2Type;
//import com.example.tyfserver.auth.dto.TokenResponse;
//import com.example.tyfserver.common.util.S3Connector;
//import com.example.tyfserver.common.util.SmtpMailConnector;
//import com.example.tyfserver.donation.domain.Donation;
//import com.example.tyfserver.donation.domain.DonationStatus;
//import com.example.tyfserver.donation.repository.DonationRepository;
//import com.example.tyfserver.member.domain.Account;
//import com.example.tyfserver.member.domain.AccountStatus;
//import com.example.tyfserver.member.domain.Exchange;
//import com.example.tyfserver.member.domain.Member;
//import com.example.tyfserver.member.exception.MemberNotFoundException;
//import com.example.tyfserver.member.repository.AccountRepository;
//import com.example.tyfserver.member.repository.ExchangeRepository;
//import com.example.tyfserver.member.repository.MemberRepository;
//import com.example.tyfserver.payment.domain.Payment;
//import com.example.tyfserver.payment.repository.PaymentRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@Transactional
//class AdminServiceTest {
//
//    @Autowired
//    private AdminService adminService;
//    @Autowired
//    private AccountRepository accountRepository;
//    @Autowired
//    private ExchangeRepository exchangeRepository;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private PaymentRepository paymentRepository;
//    @Autowired
//    private DonationRepository donationRepository;
//
//    @MockBean
//    private S3Connector s3Connector;
//    @MockBean
//    private SmtpMailConnector mailConnector;
//    @MockBean
//    private AdminAccount adminAccount;
//
//    private Member member;
//
//    @BeforeEach
//    void setUp() {
//        member = new Member("email", "nickname", "pagename", Oauth2Type.GOOGLE, "profile");
//        member.addInitialAccount(accountRepository.save(new Account()));
//        memberRepository.save(member);
//    }
//
//    @AfterEach
//    void tearDown() {
//        memberRepository.delete(member);
//    }
//
//    @Test
//    @DisplayName("정산 목록 조회")
//    public void exchangeList() {
//        Exchange exchange1 = new Exchange("승윤", "tyf@gmail.com", 12000L, "123-123-123", "nickname1", "pagename1");
//        Exchange exchange2 = new Exchange("승윤", "tyf@gmail.com", 21000L, "456-456-456", "nickname2", "pagename2");
//        Exchange exchange3 = new Exchange("승윤", "tyf@gmail.com", 31000L, "789-789-789", "nickname3", "pagename3");
//        exchangeRepository.save(exchange1);
//        exchangeRepository.save(exchange2);
//        exchangeRepository.save(exchange3);
//
//        List<ExchangeResponse> responses = adminService.exchangeList();
//        assertThat(responses.get(0)).usingRecursiveComparison()
//                .ignoringFields("createdAt")
//                .isEqualTo(new ExchangeResponse("승윤", "tyf@gmail.com", 12000L, "123-123-123", "nickname1", "pagename1", LocalDateTime.now()));
//    }
//
//    @Test
//    @DisplayName("정산 승인")
//    public void approveExchange() {
//        //given
//        memberRepository.save(member);
//        Payment payment = paymentRepository.save(new Payment(13000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
//        Donation donation = new Donation(payment);
//        member.addDonation(donation);
//        donation.toExchangeable();
//        donationRepository.save(donation);
//
//        Exchange exchange = new Exchange("승윤", "tyf@gmail.com", 13000L, "123-123", "nickname", "pagename");
//        exchangeRepository.save(exchange);
//
//        //when
//        doNothing().when(mailConnector).sendExchangeApprove(anyString());
//        adminService.approveExchange(member.getPageName());
//
//        //then
//        assertThat(donation.getStatus()).isEqualTo(DonationStatus.EXCHANGED);
//        assertThat(exchangeRepository.existsByPageName("pagename")).isFalse();
//    }
//
//    @Test
//    @DisplayName("정산 승인 - 회원을 찾을 수 없는 경우")
//    public void approveExchangeMemberNotFound() {
//        assertThatThrownBy(() -> adminService.approveExchange("any"))
//                .isInstanceOf(MemberNotFoundException.class);
//    }
//
//    @Test
//    @DisplayName("정산 거절")
//    public void rejectExchange() {
//        //given
//        memberRepository.save(member);
//        Payment payment = paymentRepository.save(new Payment(13000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
//        Donation donation = new Donation(payment);
//        member.addDonation(donation);
//        donation.toExchangeable();
//        donationRepository.save(donation);
//
//        Exchange exchange = new Exchange("승윤", "tyf@gmail.com", 13000L, "123-123", "nickname", "pagename");
//        exchangeRepository.save(exchange);
//
//        //when
//        doNothing().when(mailConnector).sendExchangeReject(anyString(), anyString());
//        adminService.rejectExchange(member.getPageName(), "그냥 거절하겠다. 토 달지 말아라");
//
//        //then
//        assertThat(exchangeRepository.existsByPageName(member.getPageName())).isFalse();
//        assertThat(donation.getStatus()).isEqualTo(DonationStatus.EXCHANGEABLE);
//    }
//
//    @Test
//    @DisplayName("정산 거절 - 회원을 찾을 수 없는 경우")
//    public void rejectExchangeMemberNotFound() {
//        assertThatThrownBy(() -> adminService.rejectExchange("any", "just denied"))
//                .isInstanceOf(MemberNotFoundException.class);
//    }
//
//    @DisplayName("유효하지 않은 관리자 계정으로 로그인 요청을 한 경우")
//    @Test
//    void adminLogin() {
//        //given
//        doNothing().when(adminAccount).validateLogin(anyString(), anyString());
//
//        //when
//        TokenResponse tokenResponse = adminService.login(new AdminLoginRequest("tyf-id", "tyf-password"));
//
//        //then
//        assertThat(tokenResponse).isNotNull();
//    }
//
//    @DisplayName("유효하지 않은 관리자 계정으로 로그인 요청을 한 경우")
//    @Test
//    void adminLoginInvalidAdminAccount() {
//        //given
//        doThrow(InvalidAdminException.class)
//                .when(adminAccount).validateLogin(anyString(), anyString());
//
//        //when //then
//        assertThatThrownBy(() -> adminService.login(new AdminLoginRequest("tyf-id", "tyf-password")))
//                .isExactlyInstanceOf(InvalidAdminException.class);
//    }
//
//    @Test
//    @DisplayName("결제 계좌요청을 승인한다.")
//    public void approveAccount() {
//        //given
//        member.registerAccount("테스트유저", "1234-1234-1234", "하나", "https://test.test.png");
//
//        //when
//        doNothing().when(s3Connector).delete(anyString());
//        doNothing().when(mailConnector).sendAccountApprove(member.getEmail());
//        adminService.approveAccount(member.getId());
//
//        //then
//        assertThat(member.getAccountStatus()).isEqualTo(AccountStatus.REGISTERED);
//        assertThat(member.getAccount().getAccountHolder()).isEqualTo("테스트유저");
//        assertThat(member.getAccount().getAccountNumber()).isEqualTo("1234-1234-1234");
//        assertThat(member.getAccount().getBank()).isEqualTo("하나");
//        assertThat(member.getAccount().getBankbookUrl()).isEqualTo("https://test.test.png");
//    }
//
//    @Test
//    @DisplayName("결제 계좌요청을 반려한다. 데이터는 지우지 않음")
//    public void cancelAccount() {
//        //given
//        member.registerAccount("테스트유저", "1234-1234-1234", "하나", "https://test.test.png");
//
//        //when
//        doNothing().when(s3Connector).delete(anyString());
//        doNothing().when(mailConnector).sendAccountApprove(member.getEmail());
//        adminService.rejectAccount(member.getId(), new AccountRejectRequest("테스트취소사유"));
//
//        //then
//        assertThat(member.getAccountStatus()).isEqualTo(AccountStatus.REJECTED);
//        assertThat(member.getAccount().getAccountHolder()).isEqualTo("테스트유저");
//        assertThat(member.getAccount().getAccountNumber()).isEqualTo("1234-1234-1234");
//        assertThat(member.getAccount().getBank()).isEqualTo("하나");
//        assertThat(member.getAccount().getBankbookUrl()).isEqualTo("https://test.test.png");
//    }
//
//    @Test
//    @DisplayName("계좌 승인 요청중 목록을 반환한다.")
//    public void requestingAccounts() {
//        //given
//        member.registerAccount("테스트유저", "1234-1234-1234", "하나", "https://test.test.png");
//
//        Member member2 = new Member("email2", "nickname2", "pagename2", Oauth2Type.GOOGLE, "profile2");
//        member2.addInitialAccount(accountRepository.save(new Account()));
//        memberRepository.save(member2);
//
//        //when
//        doNothing().when(s3Connector).delete(anyString());
//        doNothing().when(mailConnector).sendAccountApprove(member.getEmail());
//        List<RequestingAccountResponse> requestingAccounts = adminService.findRequestingAccounts();
//
//        //then
//        List<Member> allMembers = memberRepository.findAll();
//        assertThat(allMembers).hasSize(2);
//        assertThat(requestingAccounts).hasSize(1);
//
//        RequestingAccountResponse response = requestingAccounts.get(0);
//        assertThat(response.getMemberId()).isEqualTo(member.getId());
//        assertThat(response.getNickname()).isEqualTo(member.getNickname());
//        assertThat(response.getPageName()).isEqualTo(member.getPageName());
//        assertThat(response.getAccountHolder()).isEqualTo(member.getAccount().getAccountHolder());
//        assertThat(response.getAccountNumber()).isEqualTo(member.getAccount().getAccountNumber());
//        assertThat(response.getBank()).isEqualTo(member.getAccount().getBank());
//        assertThat(response.getBankbookImageUrl()).isEqualTo(member.getAccount().getBankbookUrl());
//    }
//}
