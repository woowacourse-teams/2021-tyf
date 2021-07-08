package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.util.JwtTokenProvider;
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

    public LoginMember createLoginMemberByToken(String token) {
        return new LoginMember(jwtTokenProvider.findEmailByToken(token));
    }

    public void validateToken(String token) {
        jwtTokenProvider.validateToken(token);
    }
}
