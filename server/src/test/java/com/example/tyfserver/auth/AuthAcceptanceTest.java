package com.example.tyfserver.auth;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.exception.SignUpRequestException;
import com.example.tyfserver.member.dto.SignUpRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 로그인_요청() {
        return paramGet("/oauth2/login/KAKAO", "code", "code").extract();
    }

    public static ExtractableResponse<Response> 회원생성_준비를_요청() {
        return paramGet("/oauth2/ready/KAKAO", "code", "code").extract();
    }

    public static ExtractableResponse<Response> 회원생성을_요청(String email, String oauthType, String nickname, String pageName) {
        return post("/oauth2/signup", new SignUpRequest(email, oauthType, nickname, pageName)).extract();
    }

    public static TokenResponse 로그인되어_있음() {
        return 로그인_요청().as(TokenResponse.class);
    }

    @Test
    @DisplayName("성공적인 회원 가입을 하는 경우")
    public void signUp() {
        ExtractableResponse<Response> response = 회원생성을_요청("tyf@gmail.com", "GOOGLE", "myNickname", "mypagename");
        SignUpResponse signUpResponse = response.as(SignUpResponse.class);

        assertThat(signUpResponse.getPageName()).isEqualTo("mypagename");
        assertThat(signUpResponse.getToken()).isNotNull();
    }

    @Test
    @DisplayName("유효하지 않은 Request로 회원 가입을 하는 경우")
    public void signUpInvalidRequestFailed() {
        ExtractableResponse<Response> response = 회원생성을_요청("tyf@gmail.com", "GOOGLE", "myNickname", "PPP");
        SignUpRequestException signUpRequestException = response.as(SignUpRequestException.class);

        assertThat(signUpRequestException.getErrorCode()).isEqualTo(SignUpRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("로그인에 성공함")
    public void login() {
        회원생성을_요청("tyf@gmail.com", "GOOGLE", "nickname", "pagename");
        ExtractableResponse<Response> response = 로그인_요청();

        TokenResponse tokenResponse = response.as(TokenResponse.class);
        assertThat(tokenResponse.getToken()).isNotNull();
    }

}
