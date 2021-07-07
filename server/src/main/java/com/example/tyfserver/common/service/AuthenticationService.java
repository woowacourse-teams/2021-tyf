package com.example.tyfserver.common.service;

import com.example.tyfserver.common.domain.JwtTokenProvider;
import com.example.tyfserver.common.dto.LoginMember;
import com.example.tyfserver.common.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse createToken(String email) {
        String token = jwtTokenProvider.createToken(email);
        return new TokenResponse(token);
    }

    public LoginMember findMemberByToken(String token) {
        return new LoginMember(jwtTokenProvider.findEmailByToken(token));
    }

    public void validateToken(String token) {
        jwtTokenProvider.validateToken(token);
    }
}
