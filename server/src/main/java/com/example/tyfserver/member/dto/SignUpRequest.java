package com.example.tyfserver.member.dto;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    private String email;
    private String oauthType;
    private String nickname;
    private String urlName;

    public SignUpRequest(String email, String oauthType, String nickname, String urlName) {
        this.email = email;
        this.oauthType = oauthType;
        this.nickname = nickname;
        this.urlName = urlName;
    }

    public Member toMember() {
        return new Member(email, nickname, urlName, Oauth2Type.findOauth2Type(oauthType));
    }
}
