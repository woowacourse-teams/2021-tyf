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

    public CurationsResponse(String nickname, Long donationAmount, String pageName, String profileImage) {
        this.nickname = nickname;
        this.donationAmount = donationAmount;
        this.pageName = pageName;
        this.profileImage = profileImage;
    }
}
