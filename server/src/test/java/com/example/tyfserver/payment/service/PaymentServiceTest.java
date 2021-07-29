package com.example.tyfserver.payment.service;

import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.domain.PaymentInfo;
import com.example.tyfserver.payment.domain.PaymentServiceConnector;
import com.example.tyfserver.payment.domain.PaymentStatus;
import com.example.tyfserver.payment.dto.PaymentRequest;
import com.example.tyfserver.payment.dto.PaymentSaveRequest;
import com.example.tyfserver.payment.dto.PaymentSaveResponse;
import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
import com.example.tyfserver.payment.exception.PaymentRequestException;
import com.example.tyfserver.payment.repository.PaymentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PaymentServiceConnector paymentServiceConnector;

    @InjectMocks
    private PaymentService paymentService;

    @DisplayName("결제정보를 생성한다.")
    @Test
    void createPayment() {
        //given
        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1000L, "test@test.com", "pagename");
        when(memberRepository.findByPageName(Mockito.anyString()))
                .thenReturn(
                        Optional.of(MemberTest.testMember()));

        when(paymentRepository.save(Mockito.any(Payment.class)))
                .thenReturn(
                        new Payment(1L, paymentSaveRequest.getAmount(), paymentSaveRequest.getEmail(), paymentSaveRequest.getPageName()));

        //when
        PaymentSaveResponse paymentSaveResponse = paymentService.createPayment(paymentSaveRequest);

        //then
        Assertions.assertThat(paymentSaveResponse.getMerchantUid()).isEqualTo(1L);
    }


    @DisplayName("결제정보 승인이 완료된다.")
    @Test
    void completePayment() {
        //given
        PaymentRequest paymentRequest = new PaymentRequest("impuid", 1L);
        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(PaymentRequest.class)))
                .thenReturn(new PaymentInfo(paymentRequest.getMerchantUid(), PaymentStatus.PAID, 1000L,
                        "pagename",  paymentRequest.getImpUid(), "테스트모듈"));

        when(paymentRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(new Payment(paymentRequest.getMerchantUid(), 1000L, "test@test.com", "pagename")));

        //then
        Payment payment = paymentService.completePayment(paymentRequest);
        Assertions.assertThat(payment.getImpUid()).isEqualTo(paymentRequest.getImpUid());
        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PAID);
    }

    @DisplayName("결제상태가 지불(PAID)이 아닌 결제정보가 전달되면 승인이 실패 한다.")
    @Test
    void failCompletePaymentNotPaid() {
        //given
        PaymentRequest paymentRequest = new PaymentRequest("impuid", 1L);
        PaymentInfo paymentInfo = new PaymentInfo(paymentRequest.getMerchantUid(), PaymentStatus.CANCELLED, 1000L,
                "pageName", paymentRequest.getImpUid(), "테스트모듈");

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(PaymentRequest.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(new Payment(paymentRequest.getMerchantUid(), 1000L,
                                "test@test.com", paymentInfo.getPageName())));

        //then
        Assertions.assertThatThrownBy(() -> {
             paymentService.completePayment(paymentRequest);
        }).isInstanceOf(IllegalPaymentInfoException.class)
        .hasFieldOrPropertyWithValue("errorCode", IllegalPaymentInfoException.ERROR_CODE_NOT_PAID);
    }

    @DisplayName("저장된 결제 ID와 외부 전달받은 결제 정보 ID가 다를 경우 결제승인 실패한다.")
    @Test
    void failCompletePaymentInvalidMerchantId() {
        //given
        PaymentRequest paymentRequest = new PaymentRequest("impuid", 12345L);
        PaymentInfo paymentInfo = new PaymentInfo(paymentRequest.getMerchantUid(), PaymentStatus.PAID, 1000L,
                "pageName", paymentRequest.getImpUid(), "테스트모듈");

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(PaymentRequest.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(new Payment(1L, 1000L,
                                "test@test.com", paymentInfo.getPageName())));

        //then
        Assertions.assertThatThrownBy(() -> {
            paymentService.completePayment(paymentRequest);
        }).isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue("errorCode", IllegalPaymentInfoException.ERROR_CODE_INVALID_MERCHANT_ID);
    }


    @DisplayName("저장된 결제 금액과 외부 결제모듈의 결제 금액 정보가 다를 경우 결제승인 실패한다.")
    @Test
    void failCompletePaymentInvalidAmount() {
        //given
        PaymentRequest paymentRequest = new PaymentRequest("impuid", 1L);
        PaymentInfo paymentInfo = new PaymentInfo(paymentRequest.getMerchantUid(), PaymentStatus.PAID, 1_000_000L,
                "pageName", paymentRequest.getImpUid(), "테스트모듈");

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(PaymentRequest.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(new Payment(paymentRequest.getMerchantUid(), 1000L,
                                "test@test.com", paymentInfo.getPageName())));

        //then
        Assertions.assertThatThrownBy(() -> {
            paymentService.completePayment(paymentRequest);
        }).isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue("errorCode", IllegalPaymentInfoException.ERROR_CODE_INVALID_AMOUNT);
    }

    @DisplayName("저장된 결제 PageName 정보와 전달받 PageName 정보가 다를 경우 결제승인 실패한다.")
    @Test
    void failCompletePaymentInvalidPageName() {
        //given
        PaymentRequest paymentRequest = new PaymentRequest("impuid", 1L);
        PaymentInfo paymentInfo = new PaymentInfo(paymentRequest.getMerchantUid(), PaymentStatus.PAID, 1000L,
                "pageName", paymentRequest.getImpUid(), "테스트모듈");

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(PaymentRequest.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(new Payment(paymentRequest.getMerchantUid(), 1000L,
                                "test@test.com", "diffPageName")));

        //then
        Assertions.assertThatThrownBy(() -> {
            paymentService.completePayment(paymentRequest);
        }).isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue("errorCode", IllegalPaymentInfoException.ERROR_INVALID_CREATOR);
    }
}