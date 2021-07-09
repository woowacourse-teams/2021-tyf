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
    private String oAuthType;
    private String nickName;
    private String ownPageUrl;

    public SignUpRequest(String email, String oAuthType, String nickName, String ownPageUrl) {
        this.email = email;
        this.oAuthType = oAuthType;
        this.nickName = nickName;
        this.ownPageUrl = ownPageUrl;
    }

    public Member toMember() {
        return new Member(email, nickName, ownPageUrl, Oauth2Type.findOAuth2Type(oAuthType));
    }
}
