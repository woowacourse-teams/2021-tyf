package com.example.tyfserver.banner.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerRequest {

    private String imageUrl;

    public BannerRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
