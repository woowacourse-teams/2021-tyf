package com.example.tyfserver.member.dto;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String oauthType;
    @NotBlank
    @Length(min = 2, max = 20)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$")
    private String nickname;
    @Length(min = 3, max = 20)
    @NotBlank
    @Pattern(regexp = "^[a-z0-9][a-z0-9_-]+[a-z0-9]$")
    private String pageName;

    public SignUpRequest(String email, String oauthType, String nickname, String pageName) {
        this.email = email;
        this.oauthType = oauthType;
        this.nickname = nickname;
        this.pageName = pageName;
    }

    public Member toMember() {
        return new Member(email, nickname, pageName, Oauth2Type.findOauth2Type(oauthType));
    }
}
