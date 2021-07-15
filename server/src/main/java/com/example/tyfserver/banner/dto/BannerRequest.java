package com.example.tyfserver.banner.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerRequest {

    @NotBlank
    private String imageUrl;

    public BannerRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
