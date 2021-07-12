package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import com.example.tyfserver.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;

    public String createToken(Member member) {
        return jwtTokenProvider.createToken(member.getId(), member.getEmail());
    }

    public LoginMember createLoginMemberByToken(String token) {
        return new LoginMember(
                jwtTokenProvider.findIdByToken(token),
                jwtTokenProvider.findEmailByToken(token));
    }

    public void validateToken(String token) {
        jwtTokenProvider.validateToken(token);
    }
}
