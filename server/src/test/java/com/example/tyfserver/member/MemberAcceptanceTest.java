package com.example.tyfserver.member;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.exception.AuthorizationHeaderNotFoundException;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tyfserver.auth.AuthAcceptanceTest.회원가입_후_로그인되어_있음;
import static com.example.tyfserver.auth.AuthAcceptanceTest.회원생성을_요청;
import static com.example.tyfserver.donation.DonationAcceptanceTest.후원_생성;
import static com.example.tyfserver.payment.PaymentAcceptanceTest.페이먼트_생성;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AcceptanceTest {

    private static final String INVALID_PAGE_NAME = "INVALID_PAGE_NAME!@#$";
    private static final String INVALID_NICK_NAME = "INVALID_NICK_NAME!@#$";
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

    public static ExtractableResponse<Response> 상세_포인트_조회(String token) {
        return authGet("/members/me/detailedPoint", token).extract();
    }

    public static ExtractableResponse<Response> 계좌_조회(String token) {
        return authGet("/members/me/account", token).extract();
    }

    public static ExtractableResponse<Response> 계좌_등록(MultiPartSpecification multiPartSpecification, String name, String account, String bank, String token) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("accountHolder", name);
        requestBody.put("accountNumber", account);
        requestBody.put("bank", bank);

        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .formParams(requestBody)
                .multiPart(multiPartSpecification)
                .post("/members/me/account")
                .then().extract()
                ;
    }

    public static ExtractableResponse<Response> 정산_요청(String token) {
        return authPost("/members/me/exchange", token).extract();
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
    public void validateLandingPageValidation_fail() {
        PageNameValidationRequest validationRequest = new PageNameValidationRequest("ㅁㄴㅇㄹ");
        post("/members/validate/pageName", validationRequest)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("닉네임 유효성 검사")
    public void validateNicknameValidation() {
        NicknameRequest validationRequest = new NicknameRequest("닉네임");

        post("/members/validate/nickname", validationRequest)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("닉네임 유효성 검사 - 실패")
    public void validateNicknameValidation_fail() {
        NicknameRequest validationRequest = new NicknameRequest(INVALID_NICK_NAME);
        post("/members/validate/nickname", validationRequest)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("토큰 유효성 검사")
    public void validateTokenValidation() {
        String token = jwtTokenProvider.createToken(1L, "joy@naver.com");
        TokenValidationRequest validationRequest = new TokenValidationRequest(token);
        post("/members/validate/token", validationRequest)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("토큰 유효성 검사 - 실패")
    public void validateTokenValidation_fail() {
        TokenValidationRequest validationRequest = new TokenValidationRequest(INVALID_TOKEN);
        post("/members/validate/token", validationRequest)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("창작자 정보 조회")
    public void getMemberInfo() {
        MemberResponse memberResponse = get("/members/" + member.getPageName())
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse.class);

        assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("email@email.com", "nickname", "pagename",
                        "제 페이지에 와주셔서 감사합니다!", null, false)
                );
    }

    @Test
    @DisplayName("창작자 정보 조회 - 실패: 존재하지 않는 멤버")
    public void getMemberInfo_fail() {
        ErrorResponse error = get("/members/" + INVALID_PAGE_NAME)
//                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        assertThat(error.getErrorCode())
                .isEqualTo(MemberNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("창작자 자신의 정보 조회")
    public void getMemberInfoSelf() {
        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail());
        MemberResponse MemberResponse = authGet("/members/me", token)
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse.class);

        assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("email@email.com", "nickname", "pagename",
                        "제 페이지에 와주셔서 감사합니다!", null, false)
                );
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

    @Test
    @DisplayName("큐레이션 조회")
    public void curations() {
        회원생성을_요청("user3@gmail.com", "KAKAO", "nickname3", "pagename3");
        회원생성을_요청("user2@gmail.com", "KAKAO", "nickname2", "pagename2");
        회원생성을_요청("user1@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        후원_생성("impUid", pendingResponse.getMerchantUid().toString());

        List<CurationsResponse> curations = 큐레이션_조회().body().jsonPath().getList(".", CurationsResponse.class);

        assertThat(curations).hasSize(3);
        assertThat(curations.get(0).getPageName()).isEqualTo("pagename");
        assertThat(curations.get(1).getPageName()).isEqualTo("pagename3");
        assertThat(curations.get(2).getPageName()).isEqualTo("pagename2");
    }

    @Test
    @DisplayName("상세 포인트를 조회하는 경우")
    public void detailedPoint() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("tyf@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse1 = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        후원_생성("impUid", pendingResponse1.getMerchantUid().toString()).as(DonationResponse.class).getDonationId();
        PaymentPendingResponse pendingResponse2 = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        후원_생성("impUid", pendingResponse2.getMerchantUid().toString()).as(DonationResponse.class).getDonationId();
        PaymentPendingResponse pendingResponse3 = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        후원_생성("impUid", pendingResponse3.getMerchantUid().toString()).as(DonationResponse.class).getDonationId();

        //when
        DetailedPointResponse response = 상세_포인트_조회(signUpResponse.getToken()).as(DetailedPointResponse.class);

        //then
        assertThat(response.getCurrentPoint()).isEqualTo(30000L);
        assertThat(response.getExchangeablePoint()).isEqualTo(0L);
        assertThat(response.getExchangedTotalPoint()).isEqualTo(0L);
    }

    @Test
    @DisplayName("계좌 정보 조회 요청")
    public void getAccountInfo() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        ExtractableResponse<Response> response = 계좌_조회(signUpResponse.getToken());
        AccountInfoResponse accountInfoResponse = response.as(AccountInfoResponse.class);

        assertThat(accountInfoResponse).usingRecursiveComparison()
                .isEqualTo(AccountInfoResponse.of(new Account()));
    }

    @Test
    @DisplayName("계좌 등록 요청")
    public void registerAccount() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");

        MultiPartSpecification multiPartSpecification = new MultiPartSpecBuilder("testImageBinary".getBytes())
                .mimeType(MimeTypeUtils.IMAGE_JPEG.toString())
                .controlName("bankbookImage")
                .fileName("bankbook.jpg")
                .build();

        ExtractableResponse<Response> response = 계좌_등록(multiPartSpecification, "실명",
                "1234-5678-1234", "은행", signUpResponse.getToken());

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("정산 요청 - 정산 금액이 만원 이하인 경우")
    public void requestExchangeAmountLessThanLimit() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("email@email.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        후원_생성("impUid", pendingResponse.getMerchantUid().toString()).as(DonationResponse.class).getDonationId();

        //when
        ErrorResponse errorResponse = 정산_요청(signUpResponse.getToken()).as(ErrorResponse.class);

        //then
        assertThat(errorResponse.getErrorCode()).isEqualTo(ExchangeAmountException.ERROR_CODE);
    }
}
