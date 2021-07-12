package com.example.tyfserver.member;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.member.domain.Member;

public class MemberTest {

    public static Member testMember() {
        return new Member("tyf@gmail.com", "nickname", "urlName", Oauth2Type.NAVER);
    }
}
