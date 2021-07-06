package com.example.tyfserver.banner.service;

import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.banner.domain.BannerRepository;
import com.example.tyfserver.banner.dto.BannerResponse;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BannerServiceTest {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BannerRepository bannerRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("member1@woowa.com");
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        bannerRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("배너를 생성한다")
    @Test
    void testCreateBanner() {
        // given
        String imageUrl = "image.png";

        // when
        Long bannerId = bannerService.createBanner(member, imageUrl);

        // then
        Banner findBanner = bannerRepository.findById(bannerId).get();
        assertThat(findBanner.getImageUrl()).isEqualTo(imageUrl);
    }

    @DisplayName("특정 멤버의 배너들을 조회한다")
    @Test
    void testGetBanners() {
        // given
        String email = "member2";
        Member member2 = memberRepository.save(new Member(email));
        String imageUrl = "image.png";
        bannerRepository.save(new Banner(member2, imageUrl));

        // when
        List<BannerResponse> banners = bannerService.getBanners(member2);

        // then
        assertThat(banners).hasSize(1);
        assertThat(banners.get(0).getImageUrl()).isEqualTo(imageUrl);
    }
}