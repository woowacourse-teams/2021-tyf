package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.AccountStatus;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.AccountRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.Mockito.doNothing;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Member member;

    @MockBean
    private S3Connector s3Connector;

    @MockBean
    private SmtpMailConnector smtpMailConnector;

    @BeforeEach
    void setUp() {
        member = new Member("email", "nickname", "pagename", Oauth2Type.GOOGLE, "profile");
        member.addInitialAccount(accountRepository.save(new Account()));
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        memberRepository.delete(member);
    }

    @Test
    @DisplayName("결제 계좌요청을 승인한다.")
    public void approveAccount() {
        //given
        member.registerAccount("테스트유저", "1234-1234-1234", "하나", "https://test.test.png");

        //when
        doNothing().when(s3Connector).delete(Mockito.anyString());
        doNothing().when(smtpMailConnector).sendAccountApprove(member.getEmail());
        adminService.approveAccount(member.getId());

        //then
        Assertions.assertThat(member.getAccountStatus()).isEqualTo(AccountStatus.REGISTERED);
        Assertions.assertThat(member.getAccount().getAccountHolder()).isEqualTo("테스트유저");
        Assertions.assertThat(member.getAccount().getAccountNumber()).isEqualTo("1234-1234-1234");
        Assertions.assertThat(member.getAccount().getBank()).isEqualTo("하나");
        Assertions.assertThat(member.getAccount().getBankbookUrl()).isEqualTo("https://test.test.png");
    }

    @Test
    @DisplayName("결제 계좌요청을 반려한다. 데이터는 지우지 않음")
    public void cancelAccount() {
        //given
        member.registerAccount("테스트유저", "1234-1234-1234", "하나", "https://test.test.png");

        //when
        doNothing().when(s3Connector).delete(Mockito.anyString());
        doNothing().when(smtpMailConnector).sendAccountApprove(member.getEmail());
        adminService.rejectAccount(member.getId(), new AccountRejectRequest("테스트취소사유"));

        //then
        Assertions.assertThat(member.getAccountStatus()).isEqualTo(AccountStatus.REJECTED);
        Assertions.assertThat(member.getAccount().getAccountHolder()).isEqualTo("테스트유저");
        Assertions.assertThat(member.getAccount().getAccountNumber()).isEqualTo("1234-1234-1234");
        Assertions.assertThat(member.getAccount().getBank()).isEqualTo("하나");
        Assertions.assertThat(member.getAccount().getBankbookUrl()).isEqualTo("https://test.test.png");
    }

    @Test
    @DisplayName("계좌 승인 요청중 목록을 반환한다.")
    public void requestingAccounts() {
        //given
        member.registerAccount("테스트유저", "1234-1234-1234", "하나", "https://test.test.png");

        Member member2 = new Member("email2", "nickname2", "pagename2", Oauth2Type.GOOGLE, "profile2");
        member2.addInitialAccount(accountRepository.save(new Account()));
        memberRepository.save(member2);

        //when
        doNothing().when(s3Connector).delete(Mockito.anyString());
        doNothing().when(smtpMailConnector).sendAccountApprove(member.getEmail());
        List<RequestingAccountResponse> requestingAccounts = adminService.findRequestingAccounts();

        //then
        List<Member> allMembers = memberRepository.findAll();
        Assertions.assertThat(allMembers).hasSize(2);
        Assertions.assertThat(requestingAccounts).hasSize(1);

        RequestingAccountResponse response = requestingAccounts.get(0);
        Assertions.assertThat(response.getMemberId()).isEqualTo(member.getId());
        Assertions.assertThat(response.getNickname()).isEqualTo(member.getNickname());
        Assertions.assertThat(response.getPageName()).isEqualTo(member.getPageName());
        Assertions.assertThat(response.getAccountHolder()).isEqualTo(member.getAccount().getAccountHolder());
        Assertions.assertThat(response.getAccountNumber()).isEqualTo(member.getAccount().getAccountNumber());
        Assertions.assertThat(response.getBank()).isEqualTo(member.getAccount().getBank());
        Assertions.assertThat(response.getBankbookImageUrl()).isEqualTo(member.getAccount().getBankbookUrl());
    }
}
package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.domain.AdminAccount;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.exception.InvalidAdminException;
import com.example.tyfserver.auth.dto.Oauth2Request;
import com.example.tyfserver.auth.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private AdminAccount adminAccount;

    @DisplayName("유효하지 않은 관리자 계정으로 로그인 요청을 한 경우")
    @Test
    void adminLogin() {
        //given
        doNothing().when(adminAccount).validateLogin(Mockito.anyString(), Mockito.anyString());

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
                .when(adminAccount).validateLogin(Mockito.anyString(), Mockito.anyString());

        //when //then
        assertThatThrownBy(() -> adminService.login(new AdminLoginRequest("tyf-id", "tyf-password")))
                .isExactlyInstanceOf(InvalidAdminException.class);
    }

}