package com.example.tyfserver.member.dto;

import com.example.tyfserver.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private String email;
    private String nickname;
    private String urlName;

    public MemberResponse(Member member) {
        email = member.getEmail();
        nickname = member.getNickname();
        urlName = member.getUrlName();
    }
}
