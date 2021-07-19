package com.example.tyfserver.banner;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.banner.repository.BannerRepository;
import com.example.tyfserver.banner.dto.BannerRequest;
import com.example.tyfserver.banner.dto.BannerResponse;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BannerAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Member member;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        member = memberRepository.save(MemberTest.testMember());
    }

    @AfterEach
    void tearDown() {
        bannerRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("배너 이미지를 생성, 조회한다.")
    @Test
    void 배너_이미지_생성_조회() {
        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail());
        배너_이미지_생성(token);

        List<BannerResponse> bannerResponses = authGet("/banners/me", token)
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getList(".", BannerResponse.class);

        List<Banner> banners = bannerRepository.findAllByMemberId(member.getId());

        assertThat(bannerResponses).hasSize(1);
        assertThat(bannerResponses.get(0)).usingRecursiveComparison().isEqualTo(new BannerResponse(banners.get(0)));
    }

    private void 배너_이미지_생성(String token) {
        // given
        BannerRequest bannerRequest = new BannerRequest("http://joy.com/image.png");

        // when // then
        authPost("/banners", token, bannerRequest)
                .statusCode(HttpStatus.CREATED.value());
    }
}
