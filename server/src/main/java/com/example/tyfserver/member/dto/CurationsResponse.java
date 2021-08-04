package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationsResponse {

    private String nickname;
    private Long donationAmount;
    private String pageName;
    private String profileImage;
    private String bio;

    public CurationsResponse(String nickname, Long donationAmount, String pageName, String profileImage, String bio) {
        this.nickname = nickname;
        this.donationAmount = donationAmount;
        this.pageName = pageName;
        this.profileImage = profileImage;
        this.bio = bio;
    }
}
