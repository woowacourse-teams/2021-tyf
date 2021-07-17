package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.dto.IdAndEmail;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("토큰을 이용해 LoginMember 생성")
    public void createLoginMemberByToken() {
        when(jwtTokenProvider.findIdAndEmailFromToken(Mockito.anyString()))
                .thenReturn(new IdAndEmail(1L, "email"));

        LoginMember loginMember = authenticationService.createLoginMemberByToken("token");
        assertThat(loginMember.getId()).isEqualTo(1L);
        assertThat(loginMember.getEmail()).isEqualTo("email");
    }
}
