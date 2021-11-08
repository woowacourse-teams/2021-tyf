package com.thankyou_for.auth.service;

import com.thankyou_for.auth.domain.Oauth2Type;
import com.thankyou_for.auth.dto.Oauth2Request;
import com.thankyou_for.auth.dto.SignUpResponse;
import com.thankyou_for.auth.dto.TokenResponse;
import com.thankyou_for.auth.exception.AlreadyRegisteredException;
import com.thankyou_for.auth.exception.AlreadyRegisteredInSameOauth2TypeException;
import com.thankyou_for.auth.exception.UnregisteredMemberException;
import com.thankyou_for.auth.util.Oauth2ServiceConnector;
import com.thankyou_for.member.domain.Account;
import com.thankyou_for.member.domain.Member;
import com.thankyou_for.member.dto.SignUpReadyResponse;
import com.thankyou_for.member.dto.SignUpRequest;
import com.thankyou_for.member.repository.AccountRepository;
import com.thankyou_for.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class Oauth2Service {

    private final AccountRepository accountRepository;
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
        savedMember.addInitialAccount(accountRepository.save(new Account()));

        return new SignUpResponse(authenticationService.createToken(savedMember), savedMember.getPageName());
    }

    private void validateRegisteredMember(String oauthType, Member member) {
        if (member.isSameOauthType(oauthType)) {
            throw new AlreadyRegisteredInSameOauth2TypeException(authenticationService.createToken(member));
        }
        throw new AlreadyRegisteredException(member.getOauth2Type().name());
    }
}
