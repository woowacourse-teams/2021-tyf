package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("토큰을 이용해 LoginMember 생성")
    public void createLoginMemberByToken() {
        String email = "abc@chocolate.com";
        long id = 1L;
        String token = jwtTokenProvider.createToken(id, email);

        LoginMember actual = authenticationService.createLoginMemberByToken(token);
        assertThat(actual.getEmail()).isEqualTo(email);
    }
}
