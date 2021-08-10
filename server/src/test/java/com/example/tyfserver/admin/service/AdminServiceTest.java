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