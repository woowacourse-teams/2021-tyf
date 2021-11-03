package com.thankyou_for.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponse {

    private String profileImage;

    public ProfileResponse(String profileUrl) {
        this.profileImage = profileUrl;
    }
}
