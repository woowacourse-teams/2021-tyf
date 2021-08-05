package com.example.tyfserver.donation;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.exception.DonationMessageRequestException;
import com.example.tyfserver.donation.exception.DonationNotFoundException;
import com.example.tyfserver.donation.exception.DonationRequestException;
import com.example.tyfserver.payment.dto.PaymentCompleteRequest;
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
import static com.example.tyfserver.payment.PaymentAcceptanceTest.페이먼트_생성;
import static org.assertj.core.api.Assertions.assertThat;

public class DonationAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 후원_생성(String impUid, String merchantUid) {
        return post("/donations", new PaymentCompleteRequest(impUid, merchantUid)).extract();
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
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(1000L, "donator@gmail.com", "pagename")
                .as(PaymentPendingResponse.class);

        ExtractableResponse<Response> response = 후원_생성("impUid", pendingResponse.getMerchantUid().toString());
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @Test
    @DisplayName("유효하지 않은 Request를 가진 후원 생성의 경우")
    public void createDonationInvalidRequest() {
        ErrorResponse errorResponse = 후원_생성("", UUID.randomUUID().toString())
                .as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("존재하지 않는 페이먼트에 대한 후원 생성의 경우")
    public void createDonationCreatorNotFound() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "tyfpagename");
        페이먼트_생성(1000L, "donator@gmail.com", "tyfpagename");
        ErrorResponse errorResponse = 후원_생성("impUid", UUID.randomUUID().toString())
                .as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(PaymentNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("성공적인 후원 메세지 전송")
    public void addDonationMessage() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(1000L, "donator@gmail.com", "pagename")
                .as(PaymentPendingResponse.class);
        Long donationId = 후원_생성("impUid", pendingResponse.getMerchantUid().toString())
                .as(DonationResponse.class).getDonationId();

        ExtractableResponse<Response> response = 후원_메세지_생성(donationId, "bepoz", "positive", false);
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @Test
    @DisplayName("유효하지 않은 Request의 후원 메세지 전송")
    public void addDonationMessageInvalidRequest() {
        ErrorResponse errorResponse = 후원_메세지_생성(1L, "", "positive", false)
                .as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationMessageRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("존재하지 않은 후원에 대한 후원 메세지 전송")
    public void addDonationMessageDonationNotFound() {
        ErrorResponse errorResponse = 후원_메세지_생성(1000L, "bepoz", "positive", false)
                .as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("나의 후원목록 조회")
    public void totalDonations() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(1000L, "donator@gmail.com", "pagename")
                .as(PaymentPendingResponse.class);
        Long donationId = 후원_생성("impUid", pendingResponse.getMerchantUid().toString())
                .as(DonationResponse.class).getDonationId();
        후원_메세지_생성(donationId, "donator", "thisismessage", true);

        List<DonationResponse> responses = 총_후원목록(signUpResponse.getToken())
                .body().jsonPath().getList(".", DonationResponse.class);

        assertThat(responses.get(0).getName()).isEqualTo("donator");
        assertThat(responses.get(0).getMessage()).isEqualTo("thisismessage");
    }

    @Test
    @DisplayName("공개 후원목록 조회")
    public void publicDonations() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(1000L, "donator@gmail.com", "pagename")
                .as(PaymentPendingResponse.class);
        Long donationId = 후원_생성("impUid", pendingResponse.getMerchantUid().toString())
                .as(DonationResponse.class).getDonationId();
        후원_메세지_생성(donationId, "donator", "thisismessage", true);

        List<DonationResponse> responses = 공개_후원목록("pagename")
                .body().jsonPath().getList(".", DonationResponse.class);

        assertThat(responses.get(0).getName()).isEqualTo(Message.SECRET_NAME);
        assertThat(responses.get(0).getMessage()).isEqualTo(Message.SECRET_MESSAGE);
    }
}
