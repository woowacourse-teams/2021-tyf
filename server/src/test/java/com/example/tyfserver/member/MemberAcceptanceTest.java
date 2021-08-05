package com.example.tyfserver.member;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.*;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;

import static com.example.tyfserver.auth.AuthAcceptanceTest.회원가입_후_로그인되어_있음;
import static com.example.tyfserver.auth.AuthAcceptanceTest.회원생성을_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 페이지네임_유효성_검사_요청(String pageName) {
        return post("/members/validate/pageName", new PageNameRequest(pageName)).extract();
    }

    public static ExtractableResponse<Response> 닉네임_유효성_검사_요청(String nickname) {
        return post("/members/validate/nickname", new NicknameRequest(nickname)).extract();
    }

    public static ExtractableResponse<Response> 토큰_유효성_검사_요청(String token) {
        return post("/members/validate/token", new TokenValidationRequest(token)).extract();
    }

    public static ExtractableResponse<Response> 페이지네임으로_멤버정보_조회(String pageName) {
        return get("/members/" + pageName).extract();
    }

    public static ExtractableResponse<Response> 토큰을_이용한_멤버정보_조회(String token) {
        return authGet("/members/me", token).extract();
    }

    public static ExtractableResponse<Response> 토큰을_이용한_포인트_조회(String token) {
        return authGet("/members/me/point", token).extract();
    }

    public static ExtractableResponse<Response> 큐레이션_조회() {
        return get("/members/curations").extract();
    }

    public static ExtractableResponse<Response> 프로필_수정(MultiPartSpecification multiPartSpecification, String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart(multiPartSpecification)
                .put("/members/profile")
                .then().extract()
                ;
    }

    public static ExtractableResponse<Response> 프로필_삭제(String token) {
        return authDelete("/members/profile", token).extract();
    }

    public static ExtractableResponse<Response> 자기소개_수정(String token, String bio) {
        return authPut("/members/me/bio", token, new MemberBioUpdateRequest(bio)).extract();
    }

    public static ExtractableResponse<Response> 닉네임_수정(String token, String nickname) {
        return authPut("/members/me/nickname", token, new NicknameRequest(nickname)).extract();
    }

    @Test
    @DisplayName("유효한 페이지네임 검사의 경우")
    public void validatePageName() {
        ExtractableResponse<Response> response = 페이지네임_유효성_검사_요청("pagename");

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("유효하지 않은 요청의 페이지네임 검사의 경우")
    public void validatePageNameInvalidRequest() {
        ErrorResponse errorResponse = 페이지네임_유효성_검사_요청("$%^&*").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(PageNameValidationRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("중복되는 페이지네임 검사의 경우")
    public void validatePageNameDuplicated() {
        회원생성을_요청("tyf@gmail.com", "GOOGLE", "nickname", "duplicated");
        ErrorResponse errorResponse = 페이지네임_유효성_검사_요청("duplicated").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DuplicatedPageNameException.ERROR_CODE);
    }

    @Test
    @DisplayName("유효한 닉네임 검사의 경우")
    public void validateNickname() {
        ExtractableResponse<Response> response = 닉네임_유효성_검사_요청("nickname");

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("유효하지 않은 요청의 닉네임 검사의 경우")
    public void validateNicknameInvalidRequest() {
        ErrorResponse errorResponse = 닉네임_유효성_검사_요청("#$%^&").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(NicknameValidationRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("중복되는 닉네임 검사의 경우")
    public void validateNicknameDuplicated() {
        회원생성을_요청("tyf@gmail.com", "GOOGLE", "duplicated", "pagename");
        ErrorResponse errorResponse = 닉네임_유효성_검사_요청("duplicated").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DuplicatedNicknameException.ERROR_CODE);
    }

    @Test
    @DisplayName("유효한 토큰 검사의 경우")
    public void validateToken() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@gmail.com", "GOOGLE", "nickname", "pagename");
        ExtractableResponse<Response> response = 토큰_유효성_검사_요청(signUpResponse.getToken());

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("유효하지 않은 토큰 검사의 경우")
    public void validateTokenInvalidCase() {
        ErrorResponse errorResponse = 토큰_유효성_검사_요청("invalidToken").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(InvalidTokenException.ERROR_CODE);
    }

    @Test
    @DisplayName("페이지네임으로 멤버 정보를 조회하는 경우")
    public void memberInfo() {
        회원생성을_요청("email@email.com", "KAKAO", "nickname", "pagename");
        MemberResponse memberResponse = 페이지네임으로_멤버정보_조회("pagename").as(MemberResponse.class);

        assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("email@email.com", "nickname", "pagename",
                        "제 페이지에 와주셔서 감사합니다!", null)
                );
    }

    @Test
    @DisplayName("페이지네임으로 멤버 정보를 조회하는데 찾지 못한 경우")
    public void memberInfoMemberNotFound() {
        ErrorResponse errorResponse = 페이지네임으로_멤버정보_조회("noonepagename").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(MemberNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("토큰으로 멤버 정보를 조회하는 경우")
    public void memberDetail() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        MemberResponse memberResponse = 토큰을_이용한_멤버정보_조회(signUpResponse.getToken()).as(MemberResponse.class);

        assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("email@email.com", "nickname", "pagename",
                        "제 페이지에 와주셔서 감사합니다!", null)
                );
    }

    @Test
    @DisplayName("토큰으로 포인트를 조회하는 경우")
    public void memberPoint() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        PointResponse pointResponse = 토큰을_이용한_포인트_조회(signUpResponse.getToken()).as(PointResponse.class);

        assertThat(pointResponse.getPoint()).isEqualTo(0L);
    }

    @Test
    @DisplayName("토큰으로 자기소개를 수정하는 경우")
    public void updateBio() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        ExtractableResponse<Response> response = 자기소개_수정(signUpResponse.getToken(), "updatedBio");

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("토큰으로 자기소개를 수정하는데 유효하지 않은 자기소개인 경우")
    public void updateBioInvalidRequestFailed() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        ErrorResponse errorResponse = 자기소개_수정(signUpResponse.getToken(), "").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(BioValidationRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("토큰으로 닉네임을 수정하는 경우")
    public void updateNickname() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        ExtractableResponse<Response> response = 닉네임_수정(signUpResponse.getToken(), "updatedNickname");

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("토큰으로 닉네임을 수정하는데 유효하지 않은 자기소개인 경우")
    public void updateNicknameInvalidRequestFailed() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        ErrorResponse errorResponse = 닉네임_수정(signUpResponse.getToken(), "n").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(NicknameValidationRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("토큰으로 프로필을 수정하는 경우")
    public void updateProfile() {
        MultiPartSpecification multiPartSpecification = new MultiPartSpecBuilder("testImageBinary".getBytes())
                .mimeType(MimeTypeUtils.IMAGE_JPEG.toString())
                .controlName("profileImage")
                .fileName("testImage1.jpg")
                .build();

        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        ExtractableResponse<Response> response = 프로필_수정(multiPartSpecification, signUpResponse.getToken());

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("토큰으로 프로필을 삭제하는 경우")
    public void deleteProfile() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        ExtractableResponse<Response> response = 프로필_삭제(signUpResponse.getToken());

        assertThat(response.statusCode()).isEqualTo(200);
    }

    //todo Curation 로직 고치고 추가
}
