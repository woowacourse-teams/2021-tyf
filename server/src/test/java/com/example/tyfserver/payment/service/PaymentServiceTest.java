package com.example.tyfserver.payment.service;

import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.domain.PaymentInfo;
import com.example.tyfserver.payment.domain.PaymentServiceConnector;
import com.example.tyfserver.payment.domain.PaymentStatus;
import com.example.tyfserver.payment.dto.PaymentCompleteRequest;
import com.example.tyfserver.payment.dto.PaymentPendingRequest;
import com.example.tyfserver.payment.dto.PaymentPendingResponse;
import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
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
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    private static final UUID MERCHANT_UID = UUID.randomUUID();
    private static final String IMP_UID = "imp_uid";
    private static final String PAGE_NAME = "page_name";
    private static final String EMAIL = "test@test.com";
    private static final String ERROR_CODE = "errorCode";
    private static final String MODULE = "테스트모듈";
    private static final long AMOUNT = 1000L;
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
        PaymentPendingRequest pendingRequest = new PaymentPendingRequest(AMOUNT, EMAIL, PAGE_NAME);
        when(memberRepository.findByPageName(Mockito.anyString()))
                .thenReturn(
                        Optional.of(MemberTest.testMember()));

        when(paymentRepository.save(Mockito.any(Payment.class)))
                .thenReturn(
                        new Payment(pendingRequest.getAmount(),
                                pendingRequest.getEmail(),
                                pendingRequest.getPageName(),
                                MERCHANT_UID));

        //when
        PaymentPendingResponse paymentSaveResponse = paymentService.createPayment(pendingRequest);

        //then
        Assertions.assertThat(paymentSaveResponse.getMerchantUid()).isEqualTo(MERCHANT_UID);
    }


    @DisplayName("결제정보 승인이 완료된다.")
    @Test
    void completePayment() {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(
                        new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, AMOUNT,
                                PAGE_NAME, request.getImpUid(), MODULE));

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, EMAIL, PAGE_NAME, MERCHANT_UID)));

        //then
        Payment payment = paymentService.completePayment(request);
        Assertions.assertThat(payment.getImpUid()).isEqualTo(request.getImpUid());
        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PAID);
    }

    @DisplayName("결제상태가 지불(PAID)이 아닌 결제정보가 전달되면 승인이 실패 한다.")
    @Test
    void failCompletePaymentNotPaid() {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.CANCELLED, AMOUNT,
                PAGE_NAME, request.getImpUid(), MODULE);

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, EMAIL, paymentInfo.getPageName(), MERCHANT_UID)));

        //then
        Assertions.assertThatThrownBy(() -> paymentService.completePayment(request))
                .isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue(ERROR_CODE, IllegalPaymentInfoException.ERROR_CODE_NOT_PAID);
    }

    @DisplayName("저장된 결제 ID와 외부 전달받은 결제 정보 ID가 다를 경우 결제승인 실패한다.")
    @Test
    void failCompletePaymentInvalidMerchantId() {
        //given
        UUID invalidMerchantUid = UUID.randomUUID();
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        PaymentInfo paymentInfo = new PaymentInfo(invalidMerchantUid, PaymentStatus.PAID, AMOUNT,
                PAGE_NAME, request.getImpUid(), MODULE);

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, EMAIL, paymentInfo.getPageName(), MERCHANT_UID)));

        //then
        Assertions.assertThatThrownBy(() -> paymentService.completePayment(request))
                .isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue(ERROR_CODE, IllegalPaymentInfoException.ERROR_CODE_INVALID_MERCHANT_ID);
    }


    @DisplayName("저장된 결제 금액과 외부 결제모듈의 결제 금액 정보가 다를 경우 결제승인 실패한다.")
    @Test
    void failCompletePaymentInvalidAmount() {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, 1_000_000L,
                PAGE_NAME, request.getImpUid(), MODULE);

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, EMAIL, PAGE_NAME, MERCHANT_UID)));

        //then
        Assertions.assertThatThrownBy(() -> paymentService.completePayment(request))
                .isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue(ERROR_CODE, IllegalPaymentInfoException.ERROR_CODE_INVALID_AMOUNT);
    }

    @DisplayName("저장된 결제 PageName 정보와 전달받 PageName 정보가 다를 경우 결제승인 실패한다.")
    @Test
    void failCompletePaymentInvalidPageName() {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, AMOUNT, PAGE_NAME, IMP_UID, MODULE);

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, EMAIL, "diffPageName", MERCHANT_UID)));

        //then
        Assertions.assertThatThrownBy(() -> paymentService.completePayment(request))
                .isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue(ERROR_CODE, IllegalPaymentInfoException.ERROR_CODE_INVALID_CREATOR);
    }
}