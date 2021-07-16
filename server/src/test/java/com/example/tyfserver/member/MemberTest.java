package com.example.tyfserver.member;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.member.domain.Member;

public class MemberTest {

    public static Member testMember() {
        return new Member("tyf@gmail.com", "nickname", "pageName", Oauth2Type.NAVER);
    }

    public static Member testMember2() {
        return new Member("tyf2@gmail.com", "nickname2", "pageName2", Oauth2Type.NAVER);
    }
}