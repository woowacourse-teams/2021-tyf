package com.example.tyfserver.banner.service;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.banner.domain.BannerRepository;
import com.example.tyfserver.banner.dto.BannerResponse;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public Long createBanner(LoginMember loginMember, String imageUrl) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(MemberNotFoundException::new);
        Banner banner = bannerRepository.save(new Banner(member, imageUrl));
        return banner.getId();
    }

    public List<BannerResponse> getBanners(LoginMember loginMember) {
        List<Banner> banners = bannerRepository.findAllByMemberId(loginMember.getId());
        //todo: 이 부분 비어있는 경우 고려해봐야 할 듯
        return banners.stream()
                .map(BannerResponse::new)
                .collect(Collectors.toList());
    }
}