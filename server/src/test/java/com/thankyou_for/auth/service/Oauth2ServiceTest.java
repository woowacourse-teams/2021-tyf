package com.thankyou_for.auth.service;

import com.thankyou_for.auth.domain.Oauth2Type;
import com.thankyou_for.auth.dto.Oauth2Request;
import com.thankyou_for.auth.exception.AlreadyRegisteredException;
import com.thankyou_for.auth.exception.AlreadyRegisteredInSameOauth2TypeException;
import com.thankyou_for.auth.exception.UnregisteredMemberException;
import com.thankyou_for.auth.util.Oauth2ServiceConnector;
import com.thankyou_for.member.domain.Member;
import com.thankyou_for.member.dto.SignUpRequest;
import com.thankyou_for.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import supports.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@IntegrationTest
class Oauth2ServiceTest {

    @Autowired
    private Oauth2Service oauth2Service;

    @MockBean
    private Oauth2ServiceConnector oauth2ServiceConnector;

    @Autowired
    private MemberRepository memberRepository;

    private Member member = new Member("email@gmail.com", "nickname", "pagename", Oauth2Type.GOOGLE, "profile");


    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        memberRepository.delete(member);
    }

    @Test
    @DisplayName("login")
    public void loginTest() {
        when(oauth2ServiceConnector.getEmailFromOauth2(Mockito.any(Oauth2Request.class), Mockito.anyString()))
                .thenReturn("email@gmail.com");

        Assertions.assertThat(oauth2Service.login(Oauth2Request.generateLoginInfoFrom("GOOGLE"), "code").getToken()).isNotNull();


        when(oauth2ServiceConnector.getEmailFromOauth2(Mockito.any(Oauth2Request.class), Mockito.anyString()))
                .thenReturn("invalidEmail");

        assertThatThrownBy(() -> oauth2Service.login(Oauth2Request.generateLoginInfoFrom("GOOGLE"), "code"))
                .isInstanceOf(UnregisteredMemberException.class);
    }

    @Test
    @DisplayName("readySignUp")
    public void readySignUpTest() {
        when(oauth2ServiceConnector.getEmailFromOauth2(Mockito.any(Oauth2Request.class), Mockito.anyString()))
                .thenReturn("invalidEmail");

        assertThat(oauth2Service.readySignUp(Oauth2Request.generateLoginInfoFrom("GOOGLE"), "code").getEmail())
                .isEqualTo("invalidEmail");


        when(oauth2ServiceConnector.getEmailFromOauth2(Mockito.any(Oauth2Request.class), Mockito.anyString()))
                .thenReturn("email@gmail.com");

        assertThatThrownBy(() -> oauth2Service.readySignUp(Oauth2Request.generateLoginInfoFrom("GOOGLE"), "code"))
                .isInstanceOf(AlreadyRegisteredInSameOauth2TypeException.class);
        assertThatThrownBy(() -> oauth2Service.readySignUp(Oauth2Request.generateLoginInfoFrom("KAKAO"), "code"))
                .isInstanceOf(AlreadyRegisteredException.class);
    }

    @Test
    @DisplayName("signUp")
    public void signUpTest() {
        Assertions.assertThat(
                oauth2Service.signUp(new SignUpRequest("random@gmail.com", "GOOGLE", "nickname2", "pagename2")).getPageName()
        ).isEqualTo("pagename2");
    }
}
