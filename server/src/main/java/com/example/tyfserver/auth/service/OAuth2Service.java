package com.example.tyfserver.auth.service;

import com.example.tyfserver.auth.domain.OAuth2;
import com.example.tyfserver.auth.domain.OAuth2Type;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.common.util.ApiSender;
import com.example.tyfserver.member.domain.Member;
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
public class OAuth2Service {

    private final MemberRepository memberRepository;
    private final AuthenticationService authenticationService;

    public TokenResponse login(final String oAuthType, final String code) {
        final String email = getEmailFromOauth2(oAuthType, code);

        Member findMember = memberRepository.findByEmailAndOAuth2Type(email, oAuthType)
                .orElseThrow(() -> new RuntimeException("가입되어 있지 않은 유저"));// todo 예외클래스, 메시지

        return new TokenResponse(authenticationService.createToken(findMember.getEmail()));
    }

    public SignUpResponse readySignUp(final String oAuthType, final String code) {
        final String email = getEmailFromOauth2(oAuthType, code);

        memberRepository.findByEmail(email)
                .ifPresent(member -> validateRegisteredMember(oAuthType, email, member));

        return new SignUpResponse(email, oAuthType);
    }

    private String getEmailFromOauth2(String oAuthType, String code) {
        final OAuth2 oAuth2 = OAuth2Type.findOAuth2Type(oAuthType);
        final String accessToken = requestAccessToken(code, oAuth2);
        return requestEmail(accessToken, oAuth2);
    }

    private String requestEmail(String accessToken, OAuth2 oAuth2) {
        String body = ApiSender.send(
                oAuth2.getProfileApi(),
                HttpMethod.GET,
                generateProfileRequest(accessToken)
        );

        return extractEmail(oAuth2, body);
    }

    private void validateRegisteredMember(String oAuthType, String email, Member member) {
        if (member.isSameOAuthType(oAuthType)) {
            throw new RuntimeException("token : " + authenticationService.createToken(email)); //todo: 에러 컨벤션
        }
        throw new RuntimeException(member.getOAuth2Type().name() + " 로 이미 가입된 회원입니다.");
    }

    private String requestAccessToken(String code, OAuth2 oAuth2) {
        String body = ApiSender.send(
                oAuth2.getAccessTokenApi(),
                HttpMethod.POST,
                generateAccessTokenRequest(code, oAuth2)
        );

        return extractAccessToken(body);
    }

    private HttpEntity<MultiValueMap<String, String>> generateAccessTokenRequest(String code, OAuth2 oAuth2) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", oAuth2.getClientId());
        params.add("redirect_uri", oAuth2.getRedirectUrl());
        params.add("code", code);
        params.add("client_secret", oAuth2.getClientSecret());

        return new HttpEntity<>(params, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    private String extractEmail(OAuth2 oAuth2, String response) {
        final JSONObject jsonObject = new JSONObject(response);
        return oAuth2.extractEmail(jsonObject);
    }

    private String extractAccessToken(String body) {
        final JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getString("access_token");
    }
}
