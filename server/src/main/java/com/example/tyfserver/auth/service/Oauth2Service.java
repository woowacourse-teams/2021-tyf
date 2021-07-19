package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.domain.Oauth2;
import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.Oauth2Request;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.exception.AlreadyRegisteredException;
import com.example.tyfserver.auth.exception.AlreadyRegisteredInSameOauth2TypeException;
import com.example.tyfserver.auth.exception.UnregisteredMemberException;
import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.SignUpReadyResponse;
import com.example.tyfserver.member.dto.SignUpRequest;
import com.example.tyfserver.member.repository.MemberRepository;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final MemberRepository memberRepository;
    private final AuthenticationService authenticationService;

    public TokenResponse login(final Oauth2Request oauth2Request, final String code) {
        final String email = getEmailFromOauth2(oauth2Request, code);

        Member findMember = memberRepository.findByEmailAndOauth2Type(email, Oauth2Type.findOauth2Type(oauth2Request.getType()))
                .orElseThrow(UnregisteredMemberException::new);

        return new TokenResponse(authenticationService.createToken(findMember));
    }

    public SignUpReadyResponse readySignUp(final Oauth2Request oauth2Request, final String code) {
        final String email = getEmailFromOauth2(oauth2Request, code);

        memberRepository.findByEmail(email)
                .ifPresent(member -> validateRegisteredMember(oauth2Request.getType(), member));

        return new SignUpReadyResponse(email, oauth2Request.getType());
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember();
        Member persistMember = memberRepository.save(member);

        return new SignUpResponse(authenticationService.createToken(persistMember), persistMember.getPageName());
    }

    private String getEmailFromOauth2(Oauth2Request oauth2Request, String code) {
        final String accessToken = requestAccessToken(code, oauth2Request);
        return requestEmail(accessToken, oauth2Request);
    }

    private String requestAccessToken(String code, Oauth2Request oauth2Request) {
        String body = ApiSender.send(
                oauth2Request.getAccessTokenApi(),
                HttpMethod.POST,
                generateAccessTokenRequest(code, oauth2Request)
        );

        return extractAccessToken(body);
    }

    private String requestEmail(String accessToken, Oauth2Request oauth2Request) {
        String body = ApiSender.send(
                oauth2Request.getProfileApi(),
                HttpMethod.GET,
                generateProfileRequest(accessToken)
        );

        Oauth2 oauth2 = Oauth2Type.findOauth2(oauth2Request.getType());
        return extractEmail(oauth2, body);
    }

    private void validateRegisteredMember(String oauthType, Member member) {
        if (member.isSameOauthType(oauthType)) {
            throw new AlreadyRegisteredInSameOauth2TypeException(authenticationService.createToken(member));
        }
        throw new AlreadyRegisteredException(member.getOauth2Type().name());
    }

    private HttpEntity<MultiValueMap<String, String>> generateAccessTokenRequest(String code, Oauth2Request oauth2Request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", oauth2Request.getClientId());
        params.add("redirect_uri", oauth2Request.getRedirectUrl());
        params.add("code", code);
        params.add("client_secret", oauth2Request.getClientSecret());

        return new HttpEntity<>(params, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        headers.setBearerAuth(accessToken);
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
