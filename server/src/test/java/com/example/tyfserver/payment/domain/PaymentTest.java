package com.example.tyfserver.payment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    @DisplayName("결제 정보 유효성 검사 통과 시, 결제가 완료된다.")
    void testComplete() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(1L, PaymentStatus.PAID, 1000L,
                "test", "test", "테스트모듈");
        Payment payment = new Payment(1L,1000L, "test@test.com", "test");

        //when
        payment.complete(paymentInfo);

        //then
        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PAID);
    }

    @DisplayName("결제 정보의 상태가 PAID가 아니라면 저장된 결제 데이터의 상태를 결제 정보의 상태로 동기화 시키고 결제 실패한다.")
    @Test
    void testCompleteNotPaid() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(1L, PaymentStatus.CANCELLED, 1000L,
                "test", "test", "테스트모듈");
        Payment payment = new Payment(1L,1000L, "test@test.com", "test");

        //when
        //then
        Assertions.assertThatThrownBy(() -> {
            payment.complete(paymentInfo);
        }).isInstanceOf(RuntimeException.class);

        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.CANCELLED);
    }

    @DisplayName("결제 정보와 저장된 결제 데이터의 id가 다르다면 결제 실패한다.")
    @Test
    void testCompleteWhenIdDiff() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(2L, PaymentStatus.PAID, 1000L,
                "test", "test", "테스트모듈");
        Payment payment = new Payment(1L,1000L, "test@test.com", "test");

        //when
        //then
        Assertions.assertThatThrownBy(() -> {
            payment.complete(paymentInfo);
        }).isInstanceOf(RuntimeException.class);

        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.INVALID);
    }

    @DisplayName("결제 정보와 저장된 결제 데이터의 결제금액이 다르다면 결제 실패한다.")
    @Test
    void testCompleteWhenAmountDiff() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(1L, PaymentStatus.PAID, 10000000L,
                "test", "test", "테스트모듈");
        Payment payment = new Payment(1L,1000L, "test@test.com", "test");

        //when
        //then
        Assertions.assertThatThrownBy(() -> {
            payment.complete(paymentInfo);
        }).isInstanceOf(RuntimeException.class);

        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.INVALID);
    }

    @DisplayName("결제 정보와 저장된 결제 데이터의 창작자 페이지 이름이 다르다면 결제 실패한다.")
    @Test
    void testCompleteWhenPageNameDiff() {
        //given
        PaymentInfo paymentInfo = new PaymentInfo(1L, PaymentStatus.PAID, 1000L,
                "fake", "test", "테스트모듈");
        Payment payment = new Payment(1L,1000L, "test@test.com", "test");

        //when
        //then
        Assertions.assertThatThrownBy(() -> {
            payment.complete(paymentInfo);
        }).isInstanceOf(RuntimeException.class);

        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.INVALID);
    }
}