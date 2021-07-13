package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.dto.IdAndEmail;
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
        IdAndEmail idAndEmail = jwtTokenProvider.findIdAndEmailFromToken(token);
        return new LoginMember(idAndEmail.getId(), idAndEmail.getEmail());
    }

    public void validateToken(String token) {
        jwtTokenProvider.validateToken(token);
    }
}
