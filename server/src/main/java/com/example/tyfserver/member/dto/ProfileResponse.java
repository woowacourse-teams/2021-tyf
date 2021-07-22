package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponse {

    private String profileUrl;

    public ProfileResponse(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
