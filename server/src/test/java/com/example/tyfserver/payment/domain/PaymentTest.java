package com.example.tyfserver.payment.domain;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.Point;
import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static com.example.tyfserver.payment.exception.IllegalPaymentInfoException.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.ParameterizedTest.DISPLAY_NAME_PLACEHOLDER;

public class PaymentTest {

    private static final UUID MERCHANT_UID = UUID.randomUUID();
    private static final UUID INVALID_MERCHANT_UID = UUID.randomUUID();
    private static final long AMOUNT = Item.ITEM_1.getItemPrice();
    private static final String ITEM_NAME = Item.ITEM_1.getItemName();
    private static final String IMP_UID = "test_imp_uid";
    private static final String ERROR_CODE = "errorCode";
    private static final String MODULE = "테스트모듈";
    private static final Member DONATOR = new Member("donator@email.com", "donator", "donatorPage", Oauth2Type.KAKAO,
            "https://cloudfront.net/profile.png", new Point(1_000L));

    public static Payment testPayment() {
        Payment payment = new Payment(AMOUNT, ITEM_NAME, MERCHANT_UID);
        payment.to(DONATOR);
        return payment;
    }

    @Test
    @DisplayName("결제 정보 유효성 검사 통과 시, 결제가 완료된다.")
    void testComplete() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, AMOUNT, ITEM_NAME, IMP_UID, MODULE);
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
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, status, AMOUNT, ITEM_NAME, IMP_UID, MODULE);
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
        PaymentInfo paymentInfo = new PaymentInfo(INVALID_MERCHANT_UID, PaymentStatus.PAID, AMOUNT, ITEM_NAME, IMP_UID, MODULE);
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
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, 10000000L, ITEM_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        //then
        assertThatThrownBy(() -> payment.complete(paymentInfo))
                .isExactlyInstanceOf(IllegalPaymentInfoException.class)
                .extracting(ERROR_CODE).isEqualTo(ERROR_CODE_INVALID_AMOUNT);

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.INVALID);
    }

    @DisplayName("결제 정보와 저장된 결제 데이터의 아이템 이름이 다르다면 결제 실패한다.")
    @Test
    void testCompleteWhenPageNameDiff() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, AMOUNT, Item.ITEM_3.getItemName(), IMP_UID, MODULE);
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
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.CANCELLED, AMOUNT, ITEM_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        payment.refund(paymentInfo);

        //then
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.CANCELLED);
    }

    @DisplayName("결제 정보의 상태가 CANCELLED가 아니라면 저장된 결제 데이터의 상태를 결제 정보의 상태로 동기화 시키고 환불 실패한다.")
    @ParameterizedTest(name = DISPLAY_NAME_PLACEHOLDER)
    @MethodSource("testCancelNotCancelled_source")
    void testCancelNotCancelled(PaymentStatus status) {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, status, AMOUNT, ITEM_NAME, IMP_UID, MODULE);
        Payment payment = testPayment();

        //when
        //then
        assertThatThrownBy(() -> payment.refund(paymentInfo))
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

    @DisplayName("환불이 제한된 Payment가 경우 환불이 제한됐는지 확인한다")
    @Test
    void testIsRefundBlockedIfPaymentIsRefundBlocked() {
        //given
        Payment payment = testPayment();
        payment.updateRefundFailure(new RefundFailure(0));

        //when
        boolean actual = payment.isRefundBlocked();

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("환불이 제한되지 않은 Payment가 환불이 제한됐는지 확인한다")
    @Test
    void testIsRefundBlockedIfPaymentIsNotRefundBlocked() {
        //given
        Payment payment = testPayment();

        //when
        boolean actual = payment.isRefundBlocked();

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("Payment가 환불 실패이력이 있을 때, 환불을 실패한 이력이 있는지 확인하는 기능")
    @Test
    void testHasNoRefundFailureIfPaymentHasRefundFailure() {
        //given
        Payment payment = testPayment();
        payment.updateRefundFailure(new RefundFailure());

        //when
        boolean actual = payment.hasNoRefundFailure();

        //then
        assertThat(actual).isFalse();

    }

    @DisplayName("Payment가 환불 실패이력이 없을 때, 환불을 실패한 이력이 있는지 확인하는 기능")
    @Test
    void testHasNoRefundFailureIfPaymentHasNotRefundFailure() {
        //given
        Payment payment = testPayment();

        //when
        boolean actual = payment.hasNoRefundFailure();

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("환불 보증 기간인 경우, 환불 보증 기간이 지났는지 확인하는 기능")
    @Test
    void testIsAfterRefundGuaranteeDurationIfPaymentInRefundGuaranteeDuration() {
        //given
        Payment payment = testPayment();
        payment.setCreatedAt(LocalDateTime.now().minusDays(1));

        //when
        boolean actual = payment.isAfterRefundGuaranteeDuration();

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("환불 보증 기간이 만료된 경우, 환불 보증 기간이 지났는지 확인하는 기능")
    @Test
    void testIsAfterRefundGuaranteeDurationIfPaymentAfterRefundGuaranteeDuration() {
        //given
        Payment payment = testPayment();
        payment.setCreatedAt(LocalDateTime.now().minusDays(7));

        //when
        boolean actual = payment.isAfterRefundGuaranteeDuration();

        //then
        assertThat(actual).isTrue();
    }
}
