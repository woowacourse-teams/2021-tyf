package com.example.tyfserver.payment;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.domain.CodeResendCoolTime;
import com.example.tyfserver.auth.domain.VerificationCode;
import com.example.tyfserver.auth.dto.SignUpResponse;
import com.example.tyfserver.auth.dto.VerificationFailedErrorResponse;
import com.example.tyfserver.auth.dto.VerifiedRefunder;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.auth.exception.VerificationCodeNotFoundException;
import com.example.tyfserver.auth.exception.VerificationFailedException;
import com.example.tyfserver.auth.repository.VerificationCodeRepository;
import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.exception.DonationAlreadyCancelledException;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.payment.domain.Item;
import com.example.tyfserver.payment.domain.PaymentInfo;
import com.example.tyfserver.payment.domain.PaymentServiceConnector;
import com.example.tyfserver.payment.domain.PaymentStatus;
import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.*;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.example.tyfserver.auth.AuthAcceptanceTest.회원가입_후_로그인되어_있음;
import static com.example.tyfserver.auth.AuthAcceptanceTest.회원생성을_요청;
import static com.example.tyfserver.donation.DonationAcceptanceTest.후원_메세지_생성;
import static com.example.tyfserver.donation.DonationAcceptanceTest.후원_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class PaymentAcceptanceTest extends AcceptanceTest {

    @MockBean
    private VerificationCodeRepository verificationCodeRepository;



    public static ExtractableResponse<Response> 페이먼트_생성(String itemId, String token) {
        return authPost("/payments/charge/ready", token, new PaymentPendingRequest(itemId)).extract();
    }

    public static ExtractableResponse<Response> 페이먼트_완료(String impUid, String merchantId, String token) {
        return authPost("/payments/charge", token, new PaymentCompleteRequest(impUid, merchantId)).extract();
    }

    public static ExtractableResponse<Response> 페이먼트_취소(String merchantUid, String token) {
        return authPost("/payments/cancel", token, new PaymentCancelRequest(merchantUid)).extract();
    }

    public static ExtractableResponse<Response> 환불정보_조회(String token) {
        return authGet("/payments/refund/info", token).extract();
    }

    public static SignUpResponse 충전완료_된_사용자(String email, String oauthType, String nickname, String pageName) {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음(email, oauthType, nickname, pageName);
        PaymentPendingResponse pendingResponse = 페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken()).as(PaymentPendingResponse.class);
        페이먼트_완료("impUid", pendingResponse.getMerchantUid().toString(), signUpResponse.getToken()).as(PaymentCompleteResponse.class);
        return signUpResponse;
    }

    @Test
    @DisplayName("로그인 된 후원자는 충전을 할 수 있다.")
    public void payment() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("donator@gmail.com", "KAKAO", "donator", "pagename");

        //when
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(Item.ITEM_1.name(), signUpResponse.getToken()).as(PaymentPendingResponse.class);

        //then
        assertThat(paymentPendingResponse.getMerchantUid()).isNotNull();
    }

    @Test
    @DisplayName("후원자가 유효하지 않은 ITEM으로 충전을 요청할 경우, ItemNotFoundException(payment-0014)을 응답한다.")
    public void paymentInvalidRequest() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("donator@gmail.com", "KAKAO", "donator", "pagename");

        //when
        ErrorResponse errorResponse = 페이먼트_생성("ITEM_0", signUpResponse.getToken()).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(ItemNotFoundException.ERROR_CODE);
    }


    @Test
    @DisplayName("페이먼트를 생성하는데 유효하지 않은 토큰인 경우")
    public void paymentCreatorNotFound() {
        ErrorResponse errorResponse = 페이먼트_생성(Item.ITEM_1.name(), "emptyToken").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(InvalidTokenException.ERROR_CODE);
    }

    @Test
    @DisplayName("환불을 위한 승인코드 메일을 성공적으로 보내는 경우")
    void refundVerificationReady() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid.toString(), signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        //when
        RefundVerificationReadyResponse result = 환불_인증코드_생성(merchantUid.toString(), "code", signUpResponse.getToken())
                .as(RefundVerificationReadyResponse.class);

        //then
        assertAll(
                () -> assertThat(result.getEmail()).isEqualTo("do***or@gmail.com"),
                () -> assertThat(result.getResendCoolTime()).isEqualTo(CodeResendCoolTime.DEFAULT_TTL),
                () -> assertThat(result.getTimeout()).isEqualTo(VerificationCode.DEFAULT_TTL)
        );
    }

    @Test
    @DisplayName("환불을 위한 인증코드 메일을 발급하는데 유효하지 않은 merchantUid인 경우")
    public void refundVerificationReadyInvalidMerchantUid() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String notValidMerchantUid = "not valid merchantUid";

        //when
        ErrorResponse errorResponse = 환불_인증코드_생성(notValidMerchantUid, "code", signUpResponse.getToken()).as(ErrorResponse.class);

        //then
        assertThat(errorResponse.getErrorCode()).isEqualTo(RefundVerificationReadyException.ERROR_CODE);
    }

    @Test
    @DisplayName("환불을 위한 인증코드 메일을 발급하는데 재발송 제한시간이 끝나기전에 다시 인증코드를 재요청한 경우")
    public void refundVerificationReadyResendCoolTimeIsAlive() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid.toString(), signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        환불_인증코드_생성(merchantUid, "code",signUpResponse.getToken());

        //when
        ErrorResponse errorResponse = 환불_인증코드_생성(merchantUid, "code2", signUpResponse.getToken()).as(ErrorResponse.class);

        //then
        assertThat(errorResponse.getErrorCode()).isEqualTo(CodeResendCoolTimeException.ERROR_CODE);
    }

    @DisplayName("환불을 위해 인증코드를 인증할 때, 성공적으로 일치하는 경우")
    @Test
    void refundVerification() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid.toString(), signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        String verificationCode = "123456";

        환불_인증코드_생성(merchantUid, verificationCode, signUpResponse.getToken());

        //when
        RefundVerificationResponse refundVerificationResponse =
                환불_인증코드_인증(merchantUid, verificationCode, signUpResponse.getToken()).as(RefundVerificationResponse.class);

        //then
        assertThat(refundVerificationResponse.getRefundAccessToken()).isNotNull();
    }

    @DisplayName("환불을 위해 인증코드를 인증할 때, 남은 인증 시도횟수가 없을 경우")
    @Test
    void refundVerificationNotExistRemainTryCount() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid.toString(), signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        String verificationCode = "123456";

        환불_인증코드_생성(merchantUid, verificationCode, signUpResponse.getToken());

        String unverificationCode = "000000";
        for (int i = 0; i < 10; i++) {
            환불_인증코드_실패(merchantUid, verificationCode, unverificationCode, signUpResponse.getToken());
        }

        //when
        VerificationFailedErrorResponse verificationFailedException = 환불_인증코드_인증(merchantUid, unverificationCode, signUpResponse.getToken())
                .as(VerificationFailedErrorResponse.class);

        //then
        assertThat(verificationFailedException).isNotNull();
    }

    @DisplayName("환불을 위해 인증코드를 인증할 때, 인증코드를 생성하지 않은 경우")
    @Test
    void refundVerificationNotSendVerificationCode() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid.toString(), signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        String unverificationCode = "000000";
        ErrorResponse errorResponse = 환불_인증코드_없음(merchantUid, unverificationCode, signUpResponse.getToken()).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(VerificationCodeNotFoundException.ERROR_CODE);
    }

    @DisplayName("환불을 위해 인증코드를 인증할 때, 인증코드가 틀린 경우")
    @Test
    void refundVerificationNotMatchVerificationCode() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid.toString(), signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        String verificationCode = "123456";

        환불_인증코드_생성(merchantUid, verificationCode, signUpResponse.getToken());

        String unverificationCode = "000000";
        VerificationFailedErrorResponse verificationFailedErrorResponse =
                환불_인증코드_실패(merchantUid, verificationCode, unverificationCode, signUpResponse.getToken()).as(VerificationFailedErrorResponse.class);

        assertThat(verificationFailedErrorResponse.getErrorCode()).isEqualTo(VerificationFailedException.ERROR_CODE);
        assertThat(verificationFailedErrorResponse.getRemainTryCount()).isEqualTo(9);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("환불을 위해 인증코드가 유효하지 않은 merchantUid혹은 인증코드인 경우")
    public void refundVerificationInvalidRequest(String merchantUid, String verificationCode) {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        RefundVerificationRequest verificationRequest = new RefundVerificationRequest(merchantUid, verificationCode);
        ErrorResponse errorResponse = authPost("/payments/refund/verification", signUpResponse.getToken(), verificationRequest).extract().as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(RefundVerificationException.ERROR_CODE);
    }

    private static Stream<Arguments> refundVerificationInvalidRequest() {
        return Stream.of(
                Arguments.of(null, "inputVerificationCode"),
                Arguments.of("inputMerchantUid", null),
                Arguments.of(null, null)
        );
    }

    @DisplayName("환불하려는 정보를 성공적으로 조회하는 경우")
    @Test
    void refundInfo() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("charger@gmail.com", "KAKAO", "charger", "charger");
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken()).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid, signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        //when
        환불_인증코드_생성(merchantUid, "123456", signUpResponse.getToken());
        String token = 환불_인증코드_인증(merchantUid, "123456", signUpResponse.getToken()).as(RefundVerificationResponse.class).getRefundAccessToken();
        RefundInfoResponse refundInfoResponse = 환불정보_조회(token).as(RefundInfoResponse.class);

        //then
        assertAll(
                () -> assertThat(refundInfoResponse.getAmount()).isEqualTo(Item.ITEM_100.getItemPrice()),
                () -> assertThat(refundInfoResponse.getItemName()).isEqualTo(Item.ITEM_100.getItemName())
        );
    }

    @DisplayName("환불하려는 정보를 유효하지 않은 토큰으로 조회하는 경우")
    @Test
    void refundInfoNotValidToken() {
        String token = "invalidToken";

        ErrorResponse errorResponse = 환불정보_조회(token).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(InvalidTokenException.ERROR_CODE);
    }

    @DisplayName("환불을 성공적으로 진행하는 경우")
    @Test
    void refundPayment() {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid, signUpResponse.getToken()).as(PaymentCompleteResponse.class);


        //when
        환불_인증코드_생성(merchantUid, "123456", signUpResponse.getToken()).as(RefundVerificationReadyResponse.class);
        String token = 환불_인증코드_인증(merchantUid, "123456", signUpResponse.getToken()).as(RefundVerificationResponse.class).getRefundAccessToken();

        ExtractableResponse<Response> response = 환불_요청_성공(token, merchantUid);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

 /*   @DisplayName("환불을 이미 환불된 도네이션을 대상으로 진행하는 경우")
    @Test
    void refundPaymentAlreadyCancelledDonation() {
        String pageName = "pagename";
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("donator@gmail.com", "KAKAO", "donator", "pagename");
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(Item.ITEM_1.name(), signUpResponse.getToken()).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        Long donationId = 후원_생성(pageName, 10000L, signUpResponse.getToken()).as(DonationResponse.class).getDonationId();

        후원_메세지_생성(donationId, "roki", "늘 응원합니다!", false);
        환불_인증코드_생성(merchantUid, "123456");
        String token = 환불_인증코드_인증(merchantUid, "123456").as(RefundVerificationResponse.class).getRefundAccessToken();
        환불_요청_성공(token, merchantUid);

        ErrorResponse errorResponse = 환불_요청_성공(token, merchantUid).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationAlreadyCancelledException.ERROR_CODE);
    }*/

    @DisplayName("환불을 서버의 환불정보와 결제 서버의 정보와 일치하지 않은 경우")
    @ParameterizedTest
    @MethodSource
    void refundPaymentNotMatchingRefundInfo(long amount, String pageName, PaymentStatus paymentStatus, String errorCode) {
        //given
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid, signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        //when
        환불_인증코드_생성(merchantUid, "123456", signUpResponse.getToken());
        String token = 환불_인증코드_인증(merchantUid, "123456", signUpResponse.getToken())
                .as(RefundVerificationResponse.class).getRefundAccessToken();

        ErrorResponse errorResponse = 환불_요청_실패(token, merchantUid, amount, pageName, paymentStatus).as(ErrorResponse.class);

        //then
        assertThat(errorResponse.getErrorCode()).isEqualTo(errorCode);
    }

    private static Stream<Arguments> refundPaymentNotMatchingRefundInfo() {
        return Stream.of(
                Arguments.of(10000L, "pagename", PaymentStatus.PAID, IllegalPaymentInfoException.ERROR_CODE_NOT_CANCELLED),
                Arguments.of(10000L, "pagename", PaymentStatus.PENDING, IllegalPaymentInfoException.ERROR_CODE_NOT_CANCELLED),
                Arguments.of(20000L, "pagename", PaymentStatus.CANCELLED, IllegalPaymentInfoException.ERROR_CODE_INVALID_AMOUNT),
                Arguments.of(10000L, "pagename2", PaymentStatus.CANCELLED, IllegalPaymentInfoException.ERROR_CODE_INVALID_CREATOR)
        );
    }

    @DisplayName("환불을 서버의 환불정보와 결제 서버의 merchantUid 정보와 일치하지 않은 경우")
    @Test
    void refundPaymentNotMatchingMerchantUid() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("refunder@gmail.com", "KAKAO", "refunder", "refunder");
        String merchantUid =  페이먼트_생성(Item.ITEM_100.name(), signUpResponse.getToken())
                .as(PaymentPendingResponse.class).getMerchantUid().toString();
        페이먼트_완료("impUid", merchantUid.toString(), signUpResponse.getToken()).as(PaymentCompleteResponse.class);

        환불_인증코드_생성(merchantUid, "123456", signUpResponse.getToken());
        String token = 환불_인증코드_인증(merchantUid, "123456", signUpResponse.getToken()).as(RefundVerificationResponse.class).getRefundAccessToken();

        ErrorResponse errorResponse = 환불_요청_실패(token, UUID.randomUUID().toString(), 10000L, "refunder", PaymentStatus.CANCELLED).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(IllegalPaymentInfoException.ERROR_CODE_INVALID_MERCHANT_ID);
    }

    private ExtractableResponse<Response> 환불_요청_성공(String token, String merchantUid) {
        when(paymentServiceConnector.requestPaymentRefund(any(UUID.class)))
                .thenAnswer(invocation -> new PaymentInfo(invocation.getArgument(0), PaymentStatus.CANCELLED, 10000L,
                        "pagename", "impUid", "module"));
        return authPost("/payments/refund", token, new VerifiedRefunder(merchantUid)).extract();
    }

    private ExtractableResponse<Response> 환불_요청_실패(String token, String merchantUid, long amount, String pageName, PaymentStatus paymentStatus) {
        when(paymentServiceConnector.requestPaymentRefund(any(UUID.class)))
                .thenReturn(new PaymentInfo(UUID.fromString(merchantUid), paymentStatus, amount,
                        pageName, "impUid", "module"));
        return authPost("/payments/refund", token, new VerifiedRefunder(merchantUid)).extract();
    }


    private ExtractableResponse<Response> 환불_인증코드_생성(String merchantUid, String verificationCode, String token) {
        when(verificationCodeRepository.save(any())).thenReturn(new VerificationCode(merchantUid, verificationCode));
        return authPost("/payments/refund/verification/ready", token, new RefundVerificationReadyRequest(merchantUid)).extract();
    }

    private ExtractableResponse<Response> 환불_인증코드_인증(String merchantUid, String verificationCode, String token) {
        when(verificationCodeRepository.findById(merchantUid)).thenReturn(Optional.of(new VerificationCode(merchantUid, verificationCode)));
        return authPost("/payments/refund/verification", token, new RefundVerificationRequest(merchantUid, verificationCode))
                .extract();
    }

    private ExtractableResponse<Response> 환불_인증코드_실패(String merchantUid, String verificationCode, String unverificationCode, String token) {
        when(verificationCodeRepository.findById(merchantUid)).thenReturn(Optional.of(new VerificationCode(merchantUid, verificationCode)));
        return authPost("/payments/refund/verification", token, new RefundVerificationRequest(merchantUid, unverificationCode))
                .extract();
    }

    private ExtractableResponse<Response> 환불_인증코드_없음(String merchantUid, String code, String token) {
        when(verificationCodeRepository.findById(merchantUid)).thenReturn(Optional.empty());
        return authPost("/payments/refund/verification", token, new RefundVerificationRequest(merchantUid, code))
                .extract();
    }

}
