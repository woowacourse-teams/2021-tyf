package com.example.tyfserver.payment.domain;

import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static com.example.tyfserver.payment.exception.IllegalPaymentInfoException.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.ParameterizedTest.DISPLAY_NAME_PLACEHOLDER;

class PaymentTest {

    private static final UUID MERCHANT_UID = UUID.randomUUID();
    private static final UUID INVALID_MERCHANT_UID = UUID.randomUUID();
    private static final long AMOUNT = 1000L;
    private static final String PAGE_NAME = "test";
    private static final String IMP_UID = "test_imp_uid";
    private static final String ERROR_CODE = "errorCode";
    private static final String MODULE = "테스트모듈";

    public static Payment testPayment() {
        return new Payment(MERCHANT_UID, AMOUNT, "test@test.com", PAGE_NAME);
    }

    @Test
    @DisplayName("결제 정보 유효성 검사 통과 시, 결제가 완료된다.")
    void testComplete() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, AMOUNT, PAGE_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        payment.complete(paymentInfo);

        //then
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PAID);
    }

    @DisplayName("결제 정보의 상태가 PAID가 아니라면 저장된 결제 데이터의 상태를 결제 정보의 상태로 동기화 시키고 결제 실패한다.")
    @ParameterizedTest(name = DISPLAY_NAME_PLACEHOLDER)
    @MethodSource("testCompleteNotPaid_source")
    void testCompleteNotPaid(PaymentStatus status) {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, status, AMOUNT, PAGE_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        //then
        assertThatThrownBy(() -> payment.complete(paymentInfo))
                .isExactlyInstanceOf(IllegalPaymentInfoException.class)
                .extracting(ERROR_CODE).isEqualTo(ERROR_CODE_NOT_PAID);

        assertThat(payment.getStatus()).isEqualTo(status);
    }

    private static Stream<Arguments> testCompleteNotPaid_source() {
        return Stream.of(
                Arguments.of(PaymentStatus.READY),
                Arguments.of(PaymentStatus.PENDING),
                Arguments.of(PaymentStatus.CANCELLED),
                Arguments.of(PaymentStatus.FAILED),
                Arguments.of(PaymentStatus.INVALID)
        );
    }

    @DisplayName("결제 정보와 저장된 결제 데이터의 id가 다르다면 결제 실패한다.")
    @Test
    void testCompleteWhenIdDiff() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(INVALID_MERCHANT_UID, PaymentStatus.PAID, AMOUNT, PAGE_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        //then
        assertThatThrownBy(() -> payment.complete(paymentInfo))
                .isExactlyInstanceOf(IllegalPaymentInfoException.class)
                .extracting(ERROR_CODE).isEqualTo(ERROR_CODE_INVALID_MERCHANT_ID);

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.INVALID);
    }

    @DisplayName("결제 정보와 저장된 결제 데이터의 결제금액이 다르다면 결제 실패한다.")
    @Test
    void testCompleteWhenAmountDiff() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, 10000000L, PAGE_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        //then
        assertThatThrownBy(() -> payment.complete(paymentInfo))
                .isExactlyInstanceOf(IllegalPaymentInfoException.class)
                .extracting(ERROR_CODE).isEqualTo(ERROR_CODE_INVALID_AMOUNT);

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.INVALID);
    }

    @DisplayName("결제 정보와 저장된 결제 데이터의 창작자 페이지 이름이 다르다면 결제 실패한다.")
    @Test
    void testCompleteWhenPageNameDiff() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, AMOUNT, "fake", IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        //then
        assertThatThrownBy(() -> payment.complete(paymentInfo))
                .isExactlyInstanceOf(IllegalPaymentInfoException.class)
                .extracting(ERROR_CODE).isEqualTo(ERROR_CODE_INVALID_CREATOR);

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.INVALID);
    }

    @Test
    @DisplayName("결제 정보 환불 유효성 검사 통과 시, 결제가 환불된다.")
    void testCancel() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.CANCELLED, AMOUNT, PAGE_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        payment.cancel(paymentInfo);

        //then
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.CANCELLED);
    }

    @DisplayName("결제 정보의 상태가 CANCELLED가 아니라면 저장된 결제 데이터의 상태를 결제 정보의 상태로 동기화 시키고 환불 실패한다.")
    @ParameterizedTest(name = DISPLAY_NAME_PLACEHOLDER)
    @MethodSource("testCancelNotCancelled_source")
    void testCancelNotCancelled(PaymentStatus status) {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, status, AMOUNT, PAGE_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        //then
        assertThatThrownBy(() -> payment.cancel(paymentInfo))
                .isExactlyInstanceOf(IllegalPaymentInfoException.class);

        assertThat(payment.getStatus()).isEqualTo(status);
    }

    private static Stream<Arguments> testCancelNotCancelled_source() {
        return Stream.of(
                Arguments.of(PaymentStatus.READY),
                Arguments.of(PaymentStatus.PENDING),
                Arguments.of(PaymentStatus.PAID),
                Arguments.of(PaymentStatus.FAILED),
                Arguments.of(PaymentStatus.INVALID)
        );
    }
}