package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.dto.AccountCancelRequest;
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
        adminService.cancelAccount(member.getId(), new AccountCancelRequest("테스트취소사유"));

        //then
        Assertions.assertThat(member.getAccountStatus()).isEqualTo(AccountStatus.CANCELLED);
        Assertions.assertThat(member.getAccount().getAccountHolder()).isEqualTo("테스트유저");
        Assertions.assertThat(member.getAccount().getAccountNumber()).isEqualTo("1234-1234-1234");
        Assertions.assertThat(member.getAccount().getBank()).isEqualTo("하나");
        Assertions.assertThat(member.getAccount().getBankbookUrl()).isEqualTo("https://test.test.png");
    }
}