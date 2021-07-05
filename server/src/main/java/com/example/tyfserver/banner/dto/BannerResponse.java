package com.example.tyfserver.banner.dto;

import com.example.tyfserver.banner.domain.Banner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BannerResponse {

    private Long id;
    private String imageUrl;

    public BannerResponse(Banner banner) {
        this.id = banner.getId();
        this.imageUrl = banner.getImageUrl();
    }
}
