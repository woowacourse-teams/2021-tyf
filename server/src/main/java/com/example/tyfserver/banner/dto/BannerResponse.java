package com.example.tyfserver.banner.dto;

import com.example.tyfserver.banner.domain.Banner;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BannerResponse {

    private Long id;
    private String imageUrl;

    public BannerResponse(Banner banner) {
        this.id = banner.getId();
        this.imageUrl = banner.getImageUrl();
    }
}
