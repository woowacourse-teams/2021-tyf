package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.domain.Oauth2;
import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.exception.AlreadyRegisteredException;
import com.example.tyfserver.auth.exception.AlreadyRegisteredInSameOauth2TypeException;
import com.example.tyfserver.auth.exception.UnregisteredMemberException;
import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.SignUpRequest;
import com.example.tyfserver.member.dto.SignUpResponse;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final MemberRepository memberRepository;
    private final AuthenticationService authenticationService;

    public TokenResponse login(final String oauthType, final String code) {
        final String email = getEmailFromOauth2(oauthType, code);

        Member findMember = memberRepository.findByEmailAndOauth2Type(email, oauthType)
                .orElseThrow(UnregisteredMemberException::new);

        return new TokenResponse(authenticationService.createToken(findMember));
    }

    public SignUpResponse readySignUp(final String oauthType, final String code) {
        final String email = getEmailFromOauth2(oauthType, code);

        memberRepository.findByEmail(email)
                .ifPresent(member -> validateRegisteredMember(oauthType, member));

        return new SignUpResponse(email, oauthType);
    }

    public TokenResponse signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember();
        memberRepository.save(member);
        return new TokenResponse(authenticationService.createToken(member));
    }

    private String getEmailFromOauth2(String oauthType, String code) {
        final Oauth2 oauth2 = Oauth2Type.findOauth2(oauthType);
        final String accessToken = requestAccessToken(code, oauth2);
        return requestEmail(accessToken, oauth2);
    }

    private String requestAccessToken(String code, Oauth2 oauth2) {
        String body = ApiSender.send(
                oauth2.getAccessTokenApi(),
                HttpMethod.POST,
                generateAccessTokenRequest(code, oauth2)
        );

        return extractAccessToken(body);
    }

    private String requestEmail(String accessToken, Oauth2 oauth2) {
        String body = ApiSender.send(
                oauth2.getProfileApi(),
                HttpMethod.GET,
                generateProfileRequest(accessToken)
        );

        return extractEmail(oauth2, body);
    }

    private void validateRegisteredMember(String oauthType, Member member) {
        if (member.isSameOauthType(oauthType)) {
            throw new AlreadyRegisteredInSameOauth2TypeException(authenticationService.createToken(member));
        }
        throw new AlreadyRegisteredException(member.getOauth2Type().name());
    }

    private HttpEntity<MultiValueMap<String, String>> generateAccessTokenRequest(String code, Oauth2 oauth2) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", oauth2.getClientId());
        params.add("redirect_uri", oauth2.getRedirectUrl());
        params.add("code", code);
        params.add("client_secret", oauth2.getClientSecret());

        return new HttpEntity<>(params, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    private String extractEmail(Oauth2 oauth2, String response) {
        // todo OAuth2 에서 발생한 예외 잡기
        final JSONObject jsonObject = new JSONObject(response);
        return oauth2.extractEmail(jsonObject);
    }

    private String extractAccessToken(String body) {
        // todo OAuth2 에서 발생한 예외 잡기
        final JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getString("access_token");
    }
}
