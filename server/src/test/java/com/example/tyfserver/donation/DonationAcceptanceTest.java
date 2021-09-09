package com.example.tyfserver.donation;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.exception.DonationMessageRequestException;
import com.example.tyfserver.donation.exception.DonationNotFoundException;
import com.example.tyfserver.donation.exception.DonationRequestException;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.exception.NotEnoughPointException;
import com.example.tyfserver.payment.domain.Item;
import com.example.tyfserver.payment.dto.PaymentCompleteRequest;
import com.example.tyfserver.payment.dto.PaymentCompleteResponse;
import com.example.tyfserver.payment.dto.PaymentPendingResponse;
import com.example.tyfserver.payment.exception.PaymentNotFoundException;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.example.tyfserver.auth.AuthAcceptanceTest.회원가입_후_로그인되어_있음;
import static com.example.tyfserver.auth.AuthAcceptanceTest.회원생성을_요청;
import static com.example.tyfserver.payment.PaymentAcceptanceTest.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DonationAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 후원_생성(String pageName, Long point, String token) {
        return authPost("/donations", token, new DonationRequest(pageName, point)).extract();
    }

    public static ExtractableResponse<Response> 후원_메세지_생성(Long donationId, String name, String message, boolean secret) {
        return post("/donations/" + donationId + "/messages", new DonationMessageRequest(name, message, secret)).extract();
    }

    public static ExtractableResponse<Response> 총_후원목록(String token) {
        return authGet("/donations/me", token).extract();
    }

    public static ExtractableResponse<Response> 공개_후원목록(String pageName) {
        return get("/donations/public/" + pageName).extract();
    }

    @Test
    @DisplayName("성공적인 후원 생성의 경우")
    public void createDonation() {
        //given
        SignUpResponse signUpResponse = 충전완료_된_사용자("donator@gmail.com", "KAKAO", "donator", "donator");
        회원생성을_요청("creator@gmail.com", "KAKAO", "creator", "creator");

        //when
        ExtractableResponse<Response> response = 후원_생성("creator", 10000L, signUpResponse.getToken());

        //then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @Test
    @DisplayName("존재하지 않는 회원에게는 후원 할 수 없다.")
    public void addDonationToUnRegisterMember() {
        //when
        SignUpResponse signUpResponse = 충전완료_된_사용자("donator@gmail.com", "KAKAO", "donator", "donator");
        ErrorResponse errorResponse = 후원_생성("empty", 10000L, signUpResponse.getToken()).as(ErrorResponse.class);

        //then
        assertThat(errorResponse.getErrorCode()).isEqualTo(MemberNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("보유 포인트를 초과해서 후원 할 수 없다.")
    public void addDonationWithExceedPoint() {
        //when
        SignUpResponse signUpResponse = 충전완료_된_사용자("donator@gmail.com", "KAKAO", "donator", "donator");
        회원생성을_요청("creator@gmail.com", "KAKAO", "creator", "creator");
        ErrorResponse errorResponse = 후원_생성("creator", 1_000_000L, signUpResponse.getToken()).as(ErrorResponse.class);

        //then
        assertThat(errorResponse.getErrorCode()).isEqualTo(NotEnoughPointException.ERROR_CODE);
    }


    @Test
    @DisplayName("유효하지 않은 Request를 가진 후원 생성의 경우")
    public void createDonationInvalidRequest() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("donator@gmail.com", "KAKAO", "donator", "pagename");
        ErrorResponse errorResponse = 후원_생성("", 100000L, signUpResponse.getToken()).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("성공적인 후원 메세지 전송")
    public void addDonationMessage() {
        //given
        SignUpResponse signUpResponse = 충전완료_된_사용자("donator@gmail.com", "KAKAO", "donator", "donator");

        회원생성을_요청("creator@gmail.com", "KAKAO", "creator", "creator");
        Long donationId = 후원_생성("creator", 10000L, signUpResponse.getToken()).as(DonationResponse.class).getDonationId();

        //when
        ExtractableResponse<Response> response = 후원_메세지_생성(donationId, "bepoz", "positive", false);

        //then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @Test
    @DisplayName("유효하지 않은 Request의 후원 메세지 전송")
    public void addDonationMessageInvalidRequest() {
        //when
        ErrorResponse errorResponse = 후원_메세지_생성(1L, "", "positive", false).as(ErrorResponse.class);

        //then
        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationMessageRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("존재하지 않은 후원에 대한 후원 메세지 전송")
    public void addDonationMessageDonationNotFound() {
        //when
        ErrorResponse errorResponse = 후원_메세지_생성(10000L, "bepoz", "positive", false).as(ErrorResponse.class);

        //then
        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("나의 후원목록 조회한다.")
    public void totalDonations() {
        //given
        SignUpResponse signUpResponse = 충전완료_된_사용자("donator@gmail.com", "KAKAO", "donator", "donator");
        SignUpResponse creatorSignUpResponse = 회원가입_후_로그인되어_있음("creator@gmail.com", "KAKAO", "creator", "creator");

        Long donationId = 후원_생성("creator", 10000L, signUpResponse.getToken()).as(DonationResponse.class).getDonationId();
        후원_메세지_생성(donationId, "donator", "thisismessage", true);

        //when
        List<DonationResponse> responses = 총_후원목록(creatorSignUpResponse.getToken())
                .body().jsonPath().getList(".", DonationResponse.class);

        //then
        assertThat(responses.get(0).getName()).isEqualTo("donator");
        assertThat(responses.get(0).getMessage()).isEqualTo("thisismessage");
    }

    @Test
    @DisplayName("공개 후원목록을 조회한다. 비밀 메시지의 경우 익명, 비공개 메시지입니다가 노출된다.")
    public void publicDonations() {
        //given
        SignUpResponse signUpResponse = 충전완료_된_사용자("donator@gmail.com", "KAKAO", "donator", "donator");
        회원생성을_요청("creator@gmail.com", "KAKAO", "creator", "creator");

        Long publicDonationId = 후원_생성("creator", 10000L, signUpResponse.getToken()).as(DonationResponse.class).getDonationId();
        후원_메세지_생성(publicDonationId, "donator", "thisismessage", false);

        Long secretDonationId = 후원_생성("creator", 10000L, signUpResponse.getToken()).as(DonationResponse.class).getDonationId();
        후원_메세지_생성(secretDonationId, "donator", "thisismessage", true);

        //when
        List<DonationResponse> responses = 공개_후원목록("creator")
                .body().jsonPath().getList(".", DonationResponse.class);

        //then
        assertThat(responses.get(0).getName()).isEqualTo(Message.SECRET_NAME);
        assertThat(responses.get(0).getMessage()).isEqualTo(Message.SECRET_MESSAGE);

        assertThat(responses.get(1).getName()).isEqualTo("donator");
        assertThat(responses.get(1).getMessage()).isEqualTo("thisismessage");
    }
}
