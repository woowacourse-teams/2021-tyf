package com.example.tyfserver.member.dto;

import com.example.tyfserver.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberPrivateResponse {

    private String email;
    private String nickname;
    private String pageName;

    public MemberPrivateResponse(Member member) {
        this(member.getEmail(), member.getNickname(), member.getPageName());
    }

    public MemberPrivateResponse(String email, String nickname, String pageName) {
        this.email = email;
        this.nickname = nickname;
        this.pageName = pageName;
    }
}
