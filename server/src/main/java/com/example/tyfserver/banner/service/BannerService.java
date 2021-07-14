package com.example.tyfserver.banner.service;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.banner.domain.BannerRepository;
import com.example.tyfserver.banner.dto.BannerResponse;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.member.service.MemberService;
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
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
        Banner banner = bannerRepository.save(new Banner(member, imageUrl));
        return banner.getId();
    }

    public List<BannerResponse> getBanners(LoginMember loginMember) {
        List<Banner> banners = bannerRepository.findAllByMemberId(loginMember.getId());

        return banners.stream()
                .map(BannerResponse::new)
                .collect(Collectors.toList());
    }
}
