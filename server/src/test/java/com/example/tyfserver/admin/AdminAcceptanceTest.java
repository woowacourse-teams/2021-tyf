package com.example.tyfserver.admin;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.auth.dto.TokenResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.MimeTypeUtils;

import java.util.List;

import static com.example.tyfserver.auth.AuthAcceptanceTest.회원가입_후_로그인되어_있음;
import static com.example.tyfserver.member.MemberAcceptanceTest.계좌_등록;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 정산_신청_목록_조회(String token) {
        return authGet("/admin/list/exchange", token).extract();
    }

    public static TokenResponse 관리자_로그인(String id, String password) {
        return post("/admin/login", new AdminLoginRequest(id, password)).extract().as(TokenResponse.class);
    }

    public static ExtractableResponse<Response> 요청_계좌_승인(Long memberId, String token) {
        return authPost("/admin/account/approve/" + memberId, token, "").extract();
    }

    public static ExtractableResponse<Response> 요청_계좌_반려(Long memberId, String token) {
        return authPost("/admin/account/reject/" + memberId, token, new AccountRejectRequest("거절")).extract();
    }

    public static ExtractableResponse<Response> 요청_계좌_목록_조회(String token) {
        return authGet("/admin/list/account", token).extract();
    }

    @Test
    @DisplayName("정산신청 목록 조회")
    public void exchangeList() {
        String token = 관리자_로그인("test-id", "test-password").getToken();
        List<ExchangeResponse> list = 정산_신청_목록_조회(token).body().jsonPath().getList(".", ExchangeResponse.class);

        assertThat(list).hasSize(0);
    }


    @Test
    @DisplayName("승인 요청된 계좌목록을 조회 한다.")
    public void requestingAccounts() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음(DEFAULT_EMAIL, "KAKAO", "nickname", "pagename");
        계좌_등록(generateMultiPartSpecification(), "test", "1234-1234-1234", "bank", signUpResponse.getToken());
        String token = 관리자_로그인("test-id", "test-password").getToken();


        //when
        List<RequestingAccountResponse> result = 요청_계좌_목록_조회(token).body().jsonPath().getList(".", RequestingAccountResponse.class);

        RequestingAccountResponse data = result.get(0);
        Assertions.assertThat(data.getEmail()).isEqualTo(DEFAULT_EMAIL);
        Assertions.assertThat(data.getNickname()).isEqualTo("nickname");
        Assertions.assertThat(data.getPageName()).isEqualTo("pagename");
        Assertions.assertThat(data.getAccountHolder()).isEqualTo("test");
        Assertions.assertThat(data.getAccountNumber()).isEqualTo("1234-1234-1234");
        Assertions.assertThat(data.getBank()).isEqualTo("bank");
    }

    @Test
    @DisplayName("요청된 계좌를 승인한다.")
    public void approveAccount() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음(DEFAULT_EMAIL, "KAKAO", "nickname", "pagename");
        계좌_등록(generateMultiPartSpecification(), "test", "1234-1234-1234", "bank", signUpResponse.getToken());
        String token = 관리자_로그인("test-id", "test-password").getToken();


        List<RequestingAccountResponse> requestingAccounts =
                요청_계좌_목록_조회(token).body().jsonPath().getList(".", RequestingAccountResponse.class);

        //when
        assertThat(요청_계좌_승인(requestingAccounts.get(0).getMemberId(), token).statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("요청된 계좌를 반려한다.")
    public void cancelAccount() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음(DEFAULT_EMAIL, "KAKAO", "nickname", "pagename");
        계좌_등록(generateMultiPartSpecification(), "test", "1234-1234-1234", "bank", signUpResponse.getToken());
        String token = 관리자_로그인("test-id", "test-password").getToken();

        List<RequestingAccountResponse> requestingAccounts =
                요청_계좌_목록_조회(token).body().jsonPath().getList(".", RequestingAccountResponse.class);

        //when
        assertThat(요청_계좌_반려(requestingAccounts.get(0).getMemberId(), token).statusCode()).isEqualTo(200);
    }


    private MultiPartSpecification generateMultiPartSpecification() {
        return new MultiPartSpecBuilder("testImageBinary".getBytes())
                .mimeType(MimeTypeUtils.IMAGE_JPEG.toString())
                .controlName("bankbookImage")
                .fileName("bankbook.jpg")
                .build();
    }
}
