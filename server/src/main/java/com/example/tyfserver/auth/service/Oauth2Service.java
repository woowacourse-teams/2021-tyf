package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.Oauth2Request;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.exception.AlreadyRegisteredException;
import com.example.tyfserver.auth.exception.AlreadyRegisteredInSameOauth2TypeException;
import com.example.tyfserver.auth.exception.UnregisteredMemberException;
import com.example.tyfserver.auth.util.Oauth2ServiceConnector;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.SignUpReadyResponse;
import com.example.tyfserver.member.dto.SignUpRequest;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final MemberRepository memberRepository;
    private final AuthenticationService authenticationService;
    private final Oauth2ServiceConnector oauth2ServiceConnector;

    public TokenResponse login(final Oauth2Request oauth2Request, final String code) {
        final String email = oauth2ServiceConnector.getEmailFromOauth2(oauth2Request, code);

        Member findMember = memberRepository.findByEmailAndOauth2Type(email, Oauth2Type.findOauth2Type(oauth2Request.getType()))
                .orElseThrow(UnregisteredMemberException::new);

        return new TokenResponse(authenticationService.createToken(findMember));
    }

    public SignUpReadyResponse readySignUp(final Oauth2Request oauth2Request, final String code) {
        final String email = oauth2ServiceConnector.getEmailFromOauth2(oauth2Request, code);

        memberRepository.findByEmail(email)
                .ifPresent(member -> validateRegisteredMember(oauth2Request.getType(), member));

        return new SignUpReadyResponse(email, oauth2Request.getType());
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember();
        Member savedMember = memberRepository.save(member);

        return new SignUpResponse(authenticationService.createToken(savedMember), savedMember.getPageName());
    }

    private void validateRegisteredMember(String oauthType, Member member) {
        if (member.isSameOauthType(oauthType)) {
            throw new AlreadyRegisteredInSameOauth2TypeException(authenticationService.createToken(member));
        }
        throw new AlreadyRegisteredException(member.getOauth2Type().name());
    }
}
