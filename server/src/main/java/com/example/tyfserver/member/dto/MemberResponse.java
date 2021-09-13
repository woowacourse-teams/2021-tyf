package com.example.tyfserver.member.dto;

import com.example.tyfserver.member.domain.AccountStatus;
import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private String email;
    private String nickname;
    private String pageName;
    private String bio;
    private String profileImage;
    private long point;
    private boolean bankRegistered;

    public MemberResponse(Member member) {
        this(member.getEmail(), member.getNickname(), member.getPageName(),
                member.getBio(), member.getProfileImage(), member.getAvailablePoint(), isBankRegistered(member));
    }

    private static boolean isBankRegistered(Member member) {
        return member.getAccountStatus() == AccountStatus.REGISTERED;
    }

    public MemberResponse(String email, String nickname, String pageName, String bio,
                          String profileImage, long point, boolean bankRegistered) {
        this.email = email;
        this.nickname = nickname;
        this.pageName = pageName;
        this.bio = bio;
        this.profileImage = profileImage;
        this.point = point;
        this.bankRegistered = bankRegistered;
    }
}
