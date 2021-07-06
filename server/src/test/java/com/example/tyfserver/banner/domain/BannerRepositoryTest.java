package com.example.tyfserver.banner.domain;

import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BannerRepositoryTest {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("특정 멤버의 배너들을 조회한다")
    @Test
    void testFindAllByMemberId() {
        // given
        Member member = new Member("email@woowa.com");
        memberRepository.save(member);

        Banner banner = bannerRepository.save(new Banner(member, "image.png"));

        // when
        List<Banner> banners = bannerRepository.findAllByMemberId(member.getId());

        // then
        assertThat(banners).hasSize(1);
        assertThat(banners.get(0)).isSameAs(banner);
    }
}