package com.example.tyfserver.payment;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.domain.CodeResendCoolTime;
import com.example.tyfserver.auth.domain.VerificationCode;
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
import com.example.tyfserver.payment.domain.PaymentInfo;
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

    public static ExtractableResponse<Response> 페이먼트_생성(Long amount, String email, String pageName) {
        return post("/payments", new PaymentPendingRequest(amount, email, pageName)).extract();
    }

    public static ExtractableResponse<Response> 페이먼트_취소(String merchantUid) {
        return post("/payments/cancel", new PaymentCancelRequest(merchantUid)).extract();
    }

    public static ExtractableResponse<Response> 환불정보_조회(String token) {
        return authGet("/payments/refund/info", token).extract();
    }

    @Test
    @DisplayName("페이먼트를 성공적으로 생성하는 경우")
    public void payment() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);

        assertThat(paymentPendingResponse.getMerchantUid()).isNotNull();
    }

    @Test
    @DisplayName("페이먼트를 생성하는데 유효하지 않은 Request인 경우")
    public void paymentInvalidRequest() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        ErrorResponse errorResponse = 페이먼트_생성(900L, "", "pagename").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(PaymentPendingRequestException.ERROR_CODE);
    }

    @Test
    @DisplayName("페이먼트를 생성하는데 존재하지 않는 창작자인 경우")
    public void paymentCreatorNotFound() {
        ErrorResponse errorResponse = 페이먼트_생성(10000L, "donator@gmail.com", "not exists pagename").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(MemberNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("환불을 위한 승인코드 메일을 성공적으로 보내는 경우")
    void refundVerificationReady() {
        //given
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", pageName).as(PaymentPendingResponse.class);

        RefundVerificationReadyResponse result = 환불_인증코드_생성(paymentPendingResponse.getMerchantUid().toString(), "code")
                .as(RefundVerificationReadyResponse.class);

        assertAll(
                () -> assertThat(result.getEmail()).isEqualTo("do***or@gmail.com"),
                () -> assertThat(result.getResendCoolTime()).isEqualTo(CodeResendCoolTime.DEFAULT_TTL),
                () -> assertThat(result.getTimeout()).isEqualTo(VerificationCode.DEFAULT_TTL)
        );
    }

    @Test
    @DisplayName("환불을 위한 인증코드 메일을 발급하는데 유효하지 않은 merchantUid인 경우")
    public void refundVerificationReadyInvalidMerchantUid() {
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        String notValidMerchantUid = "not valid merchantUid";
        ErrorResponse errorResponse = 환불_인증코드_생성(notValidMerchantUid, "code").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(RefundVerificationReadyException.ERROR_CODE);
    }

    @Test
    @DisplayName("환불을 위한 인증코드 메일을 발급하는데 재발송 제한시간이 끝나기전에 다시 인증코드를 재요청한 경우")
    public void refundVerificationReadyResendCoolTimeIsAlive() {
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", pageName).as(PaymentPendingResponse.class);

        환불_인증코드_생성(paymentPendingResponse.getMerchantUid().toString(), "code");
        ErrorResponse errorResponse = 환불_인증코드_생성(paymentPendingResponse.getMerchantUid().toString(), "code2").as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(CodeResendCoolTimeException.ERROR_CODE);
    }

    @DisplayName("환불을 위해 인증코드를 인증할 때, 성공적으로 일치하는 경우")
    @Test
    void refundVerification() {
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        String email = "donator@gmail.com";
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, email, pageName).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        String verificationCode = "123456";

        환불_인증코드_생성(merchantUid, verificationCode);

        RefundVerificationResponse refundVerificationResponse = 환불_인증코드_인증(merchantUid, verificationCode).as(RefundVerificationResponse.class);

        assertThat(refundVerificationResponse.getRefundAccessToken()).isNotNull();
    }

    @DisplayName("환불을 위해 인증코드를 인증할 때, 남은 인증 시도횟수가 없을 경우")
    @Test
    void refundVerificationNotExistRemainTryCount() {
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        String email = "donator@gmail.com";
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, email, pageName).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        String verificationCode = "123456";
        환불_인증코드_생성(merchantUid, verificationCode);

        String unverificationCode = "000000";
        for (int i = 0; i < 10; i++) {
            환불_인증코드_실패(merchantUid, verificationCode, unverificationCode);
        }

        VerificationFailedErrorResponse verificationFailedException = 환불_인증코드_인증(merchantUid, unverificationCode)
                .as(VerificationFailedErrorResponse.class);

        assertThat(verificationFailedException).isNotNull();
    }

    @DisplayName("환불을 위해 인증코드를 인증할 때, 인증코드를 생성하지 않은 경우")
    @Test
    void refundVerificationNotSendVerificationCode() {
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        String email = "donator@gmail.com";
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, email, pageName).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();

        String unverificationCode = "000000";
        ErrorResponse errorResponse = 환불_인증코드_없음(merchantUid, unverificationCode).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(VerificationCodeNotFoundException.ERROR_CODE);
    }

    @DisplayName("환불을 위해 인증코드를 인증할 때, 인증코드가 틀린 경우")
    @Test
    void refundVerificationNotMatchVerificationCode() {
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        String email = "donator@gmail.com";
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, email, pageName).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        String verificationCode = "123456";
        환불_인증코드_생성(merchantUid, verificationCode);

        String unverificationCode = "000000";
        VerificationFailedErrorResponse verificationFailedErrorResponse = 환불_인증코드_실패(merchantUid, verificationCode, unverificationCode).as(VerificationFailedErrorResponse.class);

        assertThat(verificationFailedErrorResponse.getErrorCode()).isEqualTo(VerificationFailedException.ERROR_CODE);
        assertThat(verificationFailedErrorResponse.getRemainTryCount()).isEqualTo(9);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("환불을 위해 인증코드가 유효하지 않은 merchantUid혹은 인증코드인 경우")
    public void refundVerificationInvalidRequest(String merchantUid, String verificationCode) {
        RefundVerificationRequest verificationRequest = new RefundVerificationRequest(merchantUid, verificationCode);
        ErrorResponse errorResponse = post("/payments/refund/verification", verificationRequest).extract().as(ErrorResponse.class);

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
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", pageName).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        Long donationId = 후원_생성("impUid", merchantUid).as(DonationResponse.class).getDonationId();

        후원_메세지_생성(donationId, "roki", "늘 응원합니다!", false);
        환불_인증코드_생성(merchantUid, "123456");
        String token = 환불_인증코드_인증(merchantUid, "123456").as(RefundVerificationResponse.class).getRefundAccessToken();

        RefundInfoResponse refundInfoResponse = 환불정보_조회(token).as(RefundInfoResponse.class);

        assertAll(
                () -> assertThat(refundInfoResponse.getCreator().getPageName()).isEqualTo("pagename"),
                () -> assertThat(refundInfoResponse.getCreator().getNickname()).isEqualTo("nickname"),
                () -> assertThat(refundInfoResponse.getDonation().getAmount()).isEqualTo(10_000L),
                () -> assertThat(refundInfoResponse.getDonation().getMessage()).isEqualTo("늘 응원합니다!"),
                () -> assertThat(refundInfoResponse.getDonation().getName()).isEqualTo("roki"),
                () -> assertThat(refundInfoResponse.getDonation().getCreatedAt()).isNotNull()
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
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", pageName).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        Long donationId = 후원_생성("impUid", merchantUid).as(DonationResponse.class).getDonationId();

        후원_메세지_생성(donationId, "roki", "늘 응원합니다!", false);
        환불_인증코드_생성(merchantUid, "123456");
        String token = 환불_인증코드_인증(merchantUid, "123456").as(RefundVerificationResponse.class).getRefundAccessToken();

        ExtractableResponse<Response> response = 환불_요청_성공(token, merchantUid);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("환불을 이미 환불된 도네이션을 대상으로 진행하는 경우")
    @Test
    void refundPaymentAlreadyCancelledDonation() {
        String pageName = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pageName);
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", pageName).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        Long donationId = 후원_생성("impUid", merchantUid).as(DonationResponse.class).getDonationId();

        후원_메세지_생성(donationId, "roki", "늘 응원합니다!", false);
        환불_인증코드_생성(merchantUid, "123456");
        String token = 환불_인증코드_인증(merchantUid, "123456").as(RefundVerificationResponse.class).getRefundAccessToken();
        환불_요청_성공(token, merchantUid);

        ErrorResponse errorResponse = 환불_요청_성공(token, merchantUid).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationAlreadyCancelledException.ERROR_CODE);
    }

    @DisplayName("환불을 서버의 환불정보와 결제 서버의 정보와 일치하지 않은 경우")
    @ParameterizedTest
    @MethodSource
    void refundPaymentNotMatchingRefundInfo(long amount, String pageName, PaymentStatus paymentStatus, String errorCode) {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        Long donationId = 후원_생성("impUid", merchantUid).as(DonationResponse.class).getDonationId();

        후원_메세지_생성(donationId, "roki", "늘 응원합니다!", false);
        환불_인증코드_생성(merchantUid, "123456");
        String token = 환불_인증코드_인증(merchantUid, "123456").as(RefundVerificationResponse.class).getRefundAccessToken();

        ErrorResponse errorResponse = 환불_요청_실패(token, merchantUid, amount, pageName, paymentStatus).as(ErrorResponse.class);

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

    @DisplayName("환불을 서버의 환불정보와 결제 서버의 merchnatUid 정보와 일치하지 않은 경우")
    @Test
    void refundPaymentNotMatchingMerchantUid() {
        String pagename = "pagename";
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", pagename);
        PaymentPendingResponse paymentPendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", pagename).as(PaymentPendingResponse.class);
        String merchantUid = paymentPendingResponse.getMerchantUid().toString();
        Long donationId = 후원_생성("impUid", merchantUid).as(DonationResponse.class).getDonationId();

        후원_메세지_생성(donationId, "roki", "늘 응원합니다!", false);
        환불_인증코드_생성(merchantUid, "123456");
        String token = 환불_인증코드_인증(merchantUid, "123456").as(RefundVerificationResponse.class).getRefundAccessToken();

        ErrorResponse errorResponse = 환불_요청_실패(token, UUID.randomUUID().toString(), 10000L, pagename, PaymentStatus.CANCELLED).as(ErrorResponse.class);

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


    private ExtractableResponse<Response> 환불_인증코드_생성(String merchantUid, String verificationCode) {
        when(verificationCodeRepository.save(any())).thenReturn(new VerificationCode(merchantUid, verificationCode));
        return post("/payments/refund/verification/ready", new RefundVerificationReadyRequest(merchantUid)).extract();
    }

    private ExtractableResponse<Response> 환불_인증코드_인증(String merchantUid, String verificationCode) {
        when(verificationCodeRepository.findById(merchantUid)).thenReturn(Optional.of(new VerificationCode(merchantUid, verificationCode)));
        return post("/payments/refund/verification", new RefundVerificationRequest(merchantUid, verificationCode))
                .extract();
    }

    private ExtractableResponse<Response> 환불_인증코드_실패(String merchantUid, String verificationCode, String unverificationCode) {
        when(verificationCodeRepository.findById(merchantUid)).thenReturn(Optional.of(new VerificationCode(merchantUid, verificationCode)));
        return post("/payments/refund/verification", new RefundVerificationRequest(merchantUid, unverificationCode))
                .extract();
    }

    private ExtractableResponse<Response> 환불_인증코드_없음(String merchantUid, String code) {
        when(verificationCodeRepository.findById(merchantUid)).thenReturn(Optional.empty());
        return post("/payments/refund/verification", new RefundVerificationRequest(merchantUid, code))
                .extract();
    }

}
