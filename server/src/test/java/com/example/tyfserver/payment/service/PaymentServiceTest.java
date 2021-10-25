package com.example.tyfserver.payment.service;

import com.example.tyfserver.auth.domain.CodeResendCoolTime;
import com.example.tyfserver.auth.domain.VerificationCode;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.dto.VerifiedRefunder;
import com.example.tyfserver.auth.repository.CodeResendCoolTimeRepository;
import com.example.tyfserver.auth.repository.VerificationCodeRepository;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.*;
import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.CannotRefundException;
import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
import com.example.tyfserver.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import supports.SliceTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SliceTest
class PaymentServiceTest {

    private static final UUID MERCHANT_UID = UUID.randomUUID();
    private static final String IMP_UID = "imp_uid";
    private static final Item ITEM = Item.ITEM_1;
    private static final long AMOUNT = Item.ITEM_1.getItemPrice();
    private static final String ITEM_NAME = Item.ITEM_1.getItemName();
    private static final String ERROR_CODE = "errorCode";
    private static final String MODULE = "테스트모듈";

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CodeResendCoolTimeRepository codeResendCoolTimeRepository;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @Mock
    private PaymentServiceConnector paymentServiceConnector;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private SmtpMailConnector smtpMailConnector;

    @InjectMocks
    private PaymentService paymentService;

    @DisplayName("결제정보를 생성한다")
    @Test
    void createPayment() {
        //given
        PaymentPendingRequest pendingRequest = new PaymentPendingRequest(ITEM.name());
        Member member = MemberTest.testMember();
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(member));

        Payment payment = new Payment(AMOUNT, ITEM_NAME, MERCHANT_UID);
        payment.to(member);
        when(paymentRepository.save(Mockito.any(Payment.class)))
                .thenReturn(payment);

        //when
        PaymentPendingResponse paymentSaveResponse =
                paymentService.createPayment(pendingRequest.getItemId(), new LoginMember(1L, "eamil@email.com"));

        //then
        assertAll(
                () -> assertThat(paymentSaveResponse.getMerchantUid()).isEqualTo(MERCHANT_UID),
                () -> assertThat(paymentSaveResponse.getAmount()).isEqualTo(AMOUNT),
                () -> assertThat(paymentSaveResponse.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(paymentSaveResponse.getItemName()).isEqualTo(ITEM_NAME)
        );
    }


    @DisplayName("결제정보 승인이 완료된다")
    @Test
    void completePayment() {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(
                        new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, AMOUNT, ITEM_NAME, request.getImpUid(), MODULE)
                );

        Payment payment = new Payment(AMOUNT, ITEM_NAME, MERCHANT_UID);
        payment.to(MemberTest.testMember());
        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(payment));

        doNothing().when(smtpMailConnector).sendChargeComplete(Mockito.any(Payment.class));

        //when
        PaymentCompleteResponse paymentCompleteResponse = paymentService.completePayment(request);

        //then
        assertThat(paymentCompleteResponse.getPoint()).isEqualTo(AMOUNT);
    }

    @DisplayName("결제상태가 지불(PAID)이 아닌 결제정보가 전달되면 승인이 실패 한다")
    @Test
    void failCompletePaymentNotPaid() {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.CANCELLED, AMOUNT,
                ITEM_NAME, request.getImpUid(), MODULE);

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(new Payment(AMOUNT, paymentInfo.getItemName(), MERCHANT_UID)));

        //then
        assertThatThrownBy(() -> paymentService.completePayment(request))
                .isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue(ERROR_CODE, IllegalPaymentInfoException.ERROR_CODE_NOT_PAID);
    }

    @DisplayName("저장된 결제 ID와 외부 전달받은 결제 정보 ID가 다를 경우 결제승인 실패한다")
    @Test
    void failCompletePaymentInvalidMerchantId() {
        //given
        UUID invalidMerchantUid = UUID.randomUUID();
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        PaymentInfo paymentInfo = new PaymentInfo(invalidMerchantUid, PaymentStatus.PAID, AMOUNT,
                ITEM_NAME, request.getImpUid(), MODULE);

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, paymentInfo.getItemName(), MERCHANT_UID)));

        //then
        assertThatThrownBy(() -> paymentService.completePayment(request))
                .isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue(ERROR_CODE, IllegalPaymentInfoException.ERROR_CODE_INVALID_MERCHANT_ID);
    }

    @DisplayName("저장된 결제 금액과 외부 결제모듈의 결제 금액 정보가 다를 경우 결제승인 실패한다")
    @Test
    void failCompletePaymentInvalidAmount() {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        PaymentInfo paymentInfo = new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, 1_000_000L,
                ITEM_NAME, request.getImpUid(), MODULE);

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(paymentInfo);

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, ITEM_NAME, MERCHANT_UID)));

        //then
        assertThatThrownBy(() -> paymentService.completePayment(request))
                .isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue(ERROR_CODE, IllegalPaymentInfoException.ERROR_CODE_INVALID_AMOUNT);
    }

    @DisplayName("저장된 결제 ItemName 정보와 전달받은 ItemName 정보가 다를 경우 결제승인 실패한다")
    @Test
    void failCompletePaymentInvalidPageName() {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());

        when(paymentServiceConnector.requestPaymentInfo(Mockito.any(UUID.class)))
                .thenReturn(
                        new PaymentInfo(MERCHANT_UID, PaymentStatus.PAID, AMOUNT, ITEM_NAME, IMP_UID, MODULE));

        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, "diffItemName", MERCHANT_UID)));

        //then
        assertThatThrownBy(() -> paymentService.completePayment(request))
                .isInstanceOf(IllegalPaymentInfoException.class)
                .hasFieldOrPropertyWithValue(ERROR_CODE, IllegalPaymentInfoException.ERROR_CODE_INVALID_CREATOR);
    }

    @DisplayName("인증번호가 생성되고 마스킹처리된 인증메일, 인증번호 유효시간, 재전송 대기시간이 응답된다")
    @Test
    void refundVerificationReady() {
        // given
        RefundVerificationReadyRequest request = new RefundVerificationReadyRequest(MERCHANT_UID.toString());
        Payment payment = PaymentTest.testPayment(LocalDateTime.now().minusDays(1));
        payment.updateStatus(PaymentStatus.PAID);

        // when
        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(payment));

        when(verificationCodeRepository.save(Mockito.any(VerificationCode.class)))
                .thenReturn(
                        new VerificationCode(MERCHANT_UID.toString(), "123456", VerificationCode.DEFAULT_TTL));

        when(codeResendCoolTimeRepository.findById(Mockito.anyString()))
                .thenReturn(
                        Optional.empty());

        when(codeResendCoolTimeRepository.save(Mockito.any(CodeResendCoolTime.class)))
                .thenReturn(
                        new CodeResendCoolTime(MERCHANT_UID.toString()));

        doNothing().when(smtpMailConnector).sendVerificationCode(Mockito.anyString(), Mockito.anyString());

        // then
        RefundVerificationReadyResponse response = paymentService.refundVerificationReady(request);
        assertThat(response.getEmail()).isEqualTo("do***or@email.com");
        assertThat(response.getTimeout()).isEqualTo(VerificationCode.DEFAULT_TTL);
        assertThat(response.getResendCoolTime()).isEqualTo(CodeResendCoolTime.DEFAULT_TTL);
    }

    @DisplayName("환불 불가한 결제건(결제완료되지 않음, 환불됨 등)은 인증메일을 보낼 수 없다.")
    @Test
    void refundVerificationReadyFail() {
        // given
        String merchantUid = UUID.randomUUID().toString();
        RefundVerificationReadyRequest request = new RefundVerificationReadyRequest(merchantUid);
        Payment payment = PaymentTest.testPayment();
        payment.updateStatus(PaymentStatus.CANCELLED);

        // when
        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(payment));

        // then
        assertThatCode(() -> paymentService.refundVerificationReady(request))
                .isExactlyInstanceOf(CannotRefundException.class);
    }

    @DisplayName("인증번호를 받아 확인되면 환불 엑세스 토큰이 응답된다")
    @Test
    void refundVerification() {
        // given
        String verificationCode = "123456";
        String refundAccessToken = "Refund Access Token";
        RefundVerificationRequest request = new RefundVerificationRequest(MERCHANT_UID.toString(), verificationCode);

        // when
        when(authenticationService.createRefundToken(Mockito.anyString()))
                .thenReturn(refundAccessToken);

        when(paymentRepository.findByMerchantUidWithRefundFailure(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(new Payment(AMOUNT, ITEM_NAME, MERCHANT_UID)));

        when(verificationCodeRepository.findById(Mockito.anyString()))
                .thenReturn(
                        Optional.of(new VerificationCode(MERCHANT_UID.toString(), verificationCode)));

        // then
        RefundVerificationResponse response = paymentService.refundVerification(request);
        assertThat(response.getRefundAccessToken()).isEqualTo(refundAccessToken);
    }

    @DisplayName("환불 엑세스 토큰이 유효하면 환불 대상 정보를 조회한다")
    @Test
    void refundInfo() {
        // given
        String merchantUid = UUID.randomUUID().toString();
        VerifiedRefunder request = new VerifiedRefunder(merchantUid);

        // when
        Payment payment = new Payment(AMOUNT, ITEM_NAME, IMP_UID, MERCHANT_UID);
        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(payment));

        // then
        RefundInfoResponse response = paymentService.refundInfo(request);
        RefundInfoResponse expectedResponse = new RefundInfoResponse(payment);

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @DisplayName("환불요청이 완료된다")
    @Test
    void refundPayment() {
        // given
        String merchantUid = UUID.randomUUID().toString();
        VerifiedRefunder request = new VerifiedRefunder(merchantUid);
        long amount = 10_000L;
        Payment payment = new Payment(1L, amount, ITEM_NAME, IMP_UID, UUID.fromString(merchantUid));
        Member member = MemberTest.testMemberWithAvailablePoint(amount);
        payment.to(member);

        // when
        when(paymentRepository.findByMerchantUid(Mockito.any(UUID.class)))
                .thenReturn(
                        Optional.of(payment));

        when(paymentServiceConnector.requestPaymentRefund(Mockito.any(UUID.class)))
                .thenReturn(new PaymentInfo(UUID.fromString(merchantUid), PaymentStatus.CANCELLED, amount, ITEM_NAME, IMP_UID, MODULE));

        // then
        assertThatCode(() -> paymentService.refundPayment(request))
                .doesNotThrowAnyException();
    }
}
