package com.example.tyfserver.member;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.exception.AuthorizationHeaderNotFoundException;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AcceptanceTest {

    private static final String INVALID_PAGE_NAME = "INVALID_PAGE_NAME";
    private static final String INVALID_TOKEN = "INVALID_TOKEN";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Member member = MemberTest.testMember();
    private Member member2 = MemberTest.testMember2();

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        member = memberRepository.save(member);
        member2 = memberRepository.save(member2);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("랜딩 페이지 유효성 검사")
    public void validateLandingPageValidation() {
        PageNameValidationRequest validationRequest = new PageNameValidationRequest("tyf");
        post("/members/validate/pageName", validationRequest)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("랜딩 페이지 유효성 검사 - 실패")
    public void validateLandingPageValidationWithNotValidCase() {
        PageNameValidationRequest validationRequest = new PageNameValidationRequest("ㅁㄴㅇㄹ");
        post("/members/validate/pageName", validationRequest)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("닉네임 유효성 검사")
    public void validateNicknameValidation() {
        NicknameValidationRequest validationRequest = new NicknameValidationRequest("닉네임");

        post("/members/validate/nickname", validationRequest)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("닉네임 유효성 검사 - 실패")
    public void validateNicknameValidationWithNotValidCase() {
        NicknameValidationRequest validationRequest = new NicknameValidationRequest("NotValidNickname!!");
        post("/members/validate/nickname", validationRequest)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("창작자 정보 조회")
    public void getMemberInfo() {
        MemberResponse memberResponse = get("/members/" + member.getPageName())
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse.class);

        assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(new MemberResponse(member));
    }

    @Test
    @DisplayName("창작자 정보 조회 - 실패: 존재하지 않는 멤버")
    public void getMemberInfo_fail() {
        ErrorResponse error = get("/members/" + INVALID_PAGE_NAME)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        assertThat(error.getErrorCode())
                .isEqualTo(MemberNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("창작자 자신의 정보 조회")
    public void getMemberInfoSelf() {
        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail());
        MemberDetailResponse memberResponse = authGet("/members/me", token)
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberDetailResponse.class);

        assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(new MemberDetailResponse(member));
    }

    @Test
    @DisplayName("창작자 자신의 정보 조회 - 실패: 인증헤더 없음")
    public void getMemberInfoSelf_fail1() {
        ErrorResponse error = get("/members/me")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        assertThat(error.getErrorCode())
                .isEqualTo(AuthorizationHeaderNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("창작자 자신의 정보 조회 - 실패: 유효하지 않은 토큰")
    public void getMemberInfoSelf_fail2() {
        ErrorResponse error = authGet("/members/me", INVALID_TOKEN)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        assertThat(error.getErrorCode())
                .isEqualTo(InvalidTokenException.ERROR_CODE);
    }

    @Test
    @DisplayName("창작자 포인트 조회")
    public void getMemberPoint() {
        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail());
        PointResponse pointResponse = authGet("/members/me/point", token)
                .statusCode(HttpStatus.OK.value())
                .extract().as(PointResponse.class);

        assertThat(pointResponse).usingRecursiveComparison()
                .isEqualTo(new PointResponse(0L));
    }

    @Test
    @DisplayName("창작자 포인트 조회 - 실패: 인증헤더 없음")
    public void getMemberPoint_fail() {
        ErrorResponse error = get("/members/me/point")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        assertThat(error.getErrorCode())
                .isEqualTo(AuthorizationHeaderNotFoundException.ERROR_CODE);
    }
}
