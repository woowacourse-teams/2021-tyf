package com.example.tyfserver.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationsResponse {

    private String nickname;
    private String pageName;
    private String profileImage;
    private String bio;

    @QueryProjection
    public CurationsResponse(String nickname, String pageName, String profileImage, String bio) {
        this.nickname = nickname;
        this.pageName = pageName;
        this.profileImage = profileImage;
        this.bio = bio;
    }
}
