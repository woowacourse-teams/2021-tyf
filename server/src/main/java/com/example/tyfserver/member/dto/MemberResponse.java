package com.example.tyfserver.member.dto;

import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private String email;
    private String nickname;
    private String pageName;
    private String bio;
    private String profileImage;
    private boolean bankRegistered;

    public MemberResponse(Member member) {
        this(member.getEmail(), member.getNickname(), member.getPageName(),
                member.getBio(), member.getProfileImage(), member.isBankRegistered());
    }

//    public MemberResponse(String email, String nickname, String pageName, String bio, String profileImage) { //todo: 이거 없애야 함
//        this.email = email;
//        this.nickname = nickname;
//        this.pageName = pageName;
//        this.bio = bio;
//        this.profileImage = profileImage;
//    }

    public MemberResponse(String email, String nickname, String pageName, String bio, String profileImage, boolean bankRegistered) {
        this.email = email;
        this.nickname = nickname;
        this.pageName = pageName;
        this.bio = bio;
        this.profileImage = profileImage;
        this.bankRegistered = bankRegistered;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPageName() {
        return pageName;
    }

    public String getBio() {
        return bio;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public boolean isBankRegistered() {
        return bankRegistered;
    }
}
