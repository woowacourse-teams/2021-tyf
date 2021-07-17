package com.example.tyfserver.banner.service;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.banner.dto.BannerResponse;
import com.example.tyfserver.banner.repository.BannerRepository;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BannerServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BannerRepository bannerRepository;

    @InjectMocks
    private BannerService bannerService;

    @Test
    @DisplayName("createBanner Test")
    public void createBannerTest() {
        //given & when
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(
                        Optional.of(MemberTest.testMember()));
        when(bannerRepository.save(Mockito.any(Banner.class)))
                .thenReturn(new Banner(1L, "image.jpg", MemberTest.testMember()));

        //then
        Long memberId = bannerService.createBanner(new LoginMember(1L, "email"), "image.jpg");
        assertThat(memberId).isEqualTo(1L);
    }

    @Test
    @DisplayName("createBanner member not found Test")
    public void createBannerNotFoundTest() {
        //given & when
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> bannerService.createBanner(new LoginMember(1L, "email"), "image.jpg"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("getBanners Test")
    public void getBannersTest() {
        //given & when
        when(bannerRepository.findAllByMemberId(Mockito.anyLong()))
                .thenReturn(Collections.singletonList(new Banner(1L, "image.jpg", MemberTest.testMember())));

        //then
        List<BannerResponse> response = bannerService.getBanners(new LoginMember(1L, "email"));
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getImageUrl()).isEqualTo("image.jpg");
    }
}
