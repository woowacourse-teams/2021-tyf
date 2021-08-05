package com.example.tyfserver.payment;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.payment.dto.PaymentCancelRequest;
import com.example.tyfserver.payment.dto.PaymentPendingRequest;
import com.example.tyfserver.payment.dto.PaymentPendingResponse;
import com.example.tyfserver.payment.exception.PaymentPendingRequestException;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.tyfserver.auth.AuthAcceptanceTest.회원생성을_요청;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 페이먼트_생성(Long amount, String email, String pageName) {
        return post("/payments", new PaymentPendingRequest(amount, email, pageName)).extract();
    }

    public static ExtractableResponse<Response> 페이먼트_취소(String merchantUid) {
        return post("/payments/cancel", new PaymentCancelRequest(merchantUid)).extract();
    }

    @Test
    @DisplayName("페이먼트를 성공적으로 생성하는 경우")
    public void payment() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "tyfpage");
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(1000L, "donator@gmail.com", "tyfpage").as(PaymentPendingResponse.class);

        assertThat(paymentPendingResponse.getMerchantUid()).isNotNull();
    }

    @Test
    @DisplayName("페이먼트를 생성하는데 유효하지 않은 Request인 경우")
    public void paymentInvalidRequest() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "tyfpage");
        ErrorResponse errorResponse = 페이먼트_생성(900L, "", "tyfpage").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(PaymentPendingRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("페이먼트를 생성하는데 존재하지 않는 창작자인 경우")
    public void paymentCreatorNotFound() {
        ErrorResponse errorResponse = 페이먼트_생성(1000L, "donator@gmail.com", "tyfpage").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(MemberNotFoundException.ERROR_CODE);
    }
}
