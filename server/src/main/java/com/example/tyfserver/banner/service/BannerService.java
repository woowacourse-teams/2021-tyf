package com.example.tyfserver.banner.service;

import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.banner.domain.BannerRepository;
import com.example.tyfserver.banner.dto.BannerResponse;
import com.example.tyfserver.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public Long createBanner(Member member, String imageUrl) {
        Banner banner = bannerRepository.save(new Banner(member, imageUrl));
        return banner.getId();
    }

    public List<BannerResponse> getBanners(Member member) {
        List<Banner> banners = bannerRepository.findAllByMemberId(member.id());

        return banners.stream()
                .map(BannerResponse::new)
                .collect(Collectors.toList());
    }
}
