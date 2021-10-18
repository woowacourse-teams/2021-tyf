package com.example.tyfserver.payment.controller;

import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.dto.VerifiedRefunder;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.payment.domain.Item;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.domain.PaymentTest;
import com.example.tyfserver.payment.dto.*;
import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
import com.example.tyfserver.payment.exception.PaymentCompleteRequestException;
import com.example.tyfserver.payment.exception.PaymentPendingRequestException;
import com.example.tyfserver.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.example.tyfserver.payment.exception.IllegalPaymentInfoException.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentController.class)
@AutoConfigureRestDocs
public class PaymentControllerTest {

    private static final UUID MERCHANT_UID = UUID.randomUUID();
    private static final Item ITEM = Item.ITEM_1;
    private static final long AMOUNT = Item.ITEM_1.getItemPrice();
    private static final String IMP_UID = "imp_uid";
    private static final String MODULE = "테스트모듈";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;

    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;

    @Test
    @DisplayName("/payments/charge/ready - success")
    public void readyCreatePayment() throws Exception {
        //given
        Payment payment = PaymentTest.testPayment();
        Member member = MemberTest.testMember();
        PaymentPendingRequest request = new PaymentPendingRequest(ITEM.name());
        PaymentPendingResponse response = new PaymentPendingResponse(payment);

        //when
        validInterceptorAndArgumentResolverLoginMemberMocking(member);
        when(paymentService.createPayment(Mockito.anyString(), Mockito.any(LoginMember.class)))
                .thenReturn(response);

        //then
        mockMvc.perform(post("/payments/charge/ready")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("merchantUid").value(payment.getMerchantUid().toString()))
                .andExpect(jsonPath("itemName").value(payment.getItemName()))
                .andExpect(jsonPath("amount").value(payment.getAmount()))
                .andExpect(jsonPath("email").value(payment.getEmail()))
                .andDo(document("paymentReady",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge/ready - 회원을 찾을 수 없음")
    public void readyCreatePaymentMemberNotFoundFailed() throws Exception {
        //given
        PaymentPendingRequest request = new PaymentPendingRequest(ITEM.name());

        //when
        validInterceptorAndArgumentResolverLoginMemberMocking(MemberTest.testMember());
        doThrow(new MemberNotFoundException())
                .when(paymentService)
                .createPayment(Mockito.anyString(), Mockito.any(LoginMember.class));

        //then
        mockMvc.perform(post("/payments/charge/ready")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("paymentReadyMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge/ready - 유효하지 않은 Request")
    public void readyCreatePaymentRequestFailed() throws Exception {
        //given
        Member member = MemberTest.testMember();
        PaymentPendingRequest request = new PaymentPendingRequest("invalidateItemName");

        validInterceptorAndArgumentResolverLoginMemberMocking(member);

        //when //then
        mockMvc.perform(post("/payments/charge/ready")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(PaymentPendingRequestException.ERROR_CODE))
                .andDo(document("paymentReadyRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge - success")
    public void createPayment() throws Exception {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        PaymentCompleteResponse response = new PaymentCompleteResponse(AMOUNT);
        Member member = MemberTest.testMember();

        //when
        validInterceptorAndArgumentResolverLoginMemberMocking(member);
        when(paymentService.completePayment(any(PaymentCompleteRequest.class)))
                .thenReturn(response);

        //then
        mockMvc.perform(post("/payments/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("point").value(AMOUNT))
                .andDo(document("createPayment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge - 회원을 찾을 수 없음")
    public void createPaymentMemberNotFoundFailed() throws Exception {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());

        //when
        validInterceptorAndArgumentResolverLoginMemberMocking(MemberTest.testMember());
        doThrow(new MemberNotFoundException())
                .when(paymentService).completePayment(any(PaymentCompleteRequest.class));

        //then
        mockMvc.perform(post("/payments/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("createPaymentMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge - 유효하지 않은 Request")
    public void createPaymentRequestFailed() throws Exception {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, "Invalid UUID value");

        //when
        Member member = MemberTest.testMember();
        validInterceptorAndArgumentResolverLoginMemberMocking(member);

        //then
        mockMvc.perform(post("/payments/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(PaymentCompleteRequestException.ERROR_CODE))
                .andDo(document("createPaymentRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge - 실 결제 정보 상태가 PAID가 아닐경우")
    public void createPaymentFailedStatusNotPaid() throws Exception {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        //when
        doThrow(IllegalPaymentInfoException.from(ERROR_CODE_NOT_PAID, "테스트모듈"))
                .when(paymentService).completePayment(any(PaymentCompleteRequest.class));

        //then
        mockMvc.perform(post("/payments/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(ERROR_CODE_NOT_PAID))
                .andDo(document("createPaymentFailedStatusNotPaid",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge - 실 결제 정보의 id와 저장된 결제정보의 id가 일치하지 않을 경우")
    public void createPaymentFailedStatusInvalidId() throws Exception {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        //when
        doThrow(IllegalPaymentInfoException.from(ERROR_CODE_INVALID_MERCHANT_ID, MODULE))
                .when(paymentService).completePayment(any(PaymentCompleteRequest.class));

        //then
        mockMvc.perform(post("/payments/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(ERROR_CODE_INVALID_MERCHANT_ID))
                .andDo(document("createPaymentFailedStatusInvalidId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge - 실 결제 정보의 Amount와 저장된 결제정보의 Amount가 일치하지 않을 경우")
    public void createPaymentFailedInvalidAmount() throws Exception {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        //when
        doThrow(IllegalPaymentInfoException.from(ERROR_CODE_INVALID_AMOUNT, MODULE))
                .when(paymentService).completePayment(any(PaymentCompleteRequest.class));

        //then
        mockMvc.perform(post("/payments/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(ERROR_CODE_INVALID_AMOUNT))
                .andDo(document("createPaymentFailedInvalidAmount",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/charge - 실 결제 정보의 PageName과 저장된 결제정보의 PageName이 일치하지 않을 경우")
    public void createPaymentFailedInvalidPageName() throws Exception {
        //given
        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_UID, MERCHANT_UID.toString());
        //when
        doThrow(IllegalPaymentInfoException.from(ERROR_CODE_INVALID_CREATOR, MODULE))
                .when(paymentService).completePayment(any(PaymentCompleteRequest.class));

        //then
        mockMvc.perform(post("/payments/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(ERROR_CODE_INVALID_CREATOR))
                .andDo(document("createPaymentFailedInvalidPageName",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/refund/verification/ready - success")
    public void refundVerificationReady() throws Exception {
        //given
        String merchantUid = UUID.randomUUID().toString();
        RefundVerificationReadyRequest request = new RefundVerificationReadyRequest(merchantUid);

        //when
        when(paymentService.refundVerificationReady(any(RefundVerificationReadyRequest.class)))
                .thenReturn(new RefundVerificationReadyResponse("test@test.com"));

        //then
        mockMvc.perform(post("/payments/refund/verification/ready")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("refundVerificationReady",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/refund/verification - success")
    public void refundVerification() throws Exception {
        //given
        String verificationCode = "123456";
        String merchantUid = UUID.randomUUID().toString();
        RefundVerificationRequest request = new RefundVerificationRequest(merchantUid, verificationCode);

        //when
        when(paymentService.refundVerification(any(RefundVerificationRequest.class)))
                .thenReturn(new RefundVerificationResponse("refundAccessToken"));

        //then
        mockMvc.perform(post("/payments/refund/verification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("refundVerification",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/refund/info - success")
    public void refundInfo() throws Exception {
        //given
        VerifiedRefunder verifiedRefunder = new VerifiedRefunder(MERCHANT_UID.toString());
        RefundInfoResponse response = new RefundInfoResponse(PaymentTest.testPayment());

        //when
        validInterceptorAndArgumentResolverVerifiedRefunderMocking(verifiedRefunder);
        when(paymentService.refundInfo(any(VerifiedRefunder.class)))
                .thenReturn(response);

        //then
        mockMvc.perform(get("/payments/refund/info")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer {refundAccessToken}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("point").value(response.getPoint()))
                .andExpect(jsonPath("price").value(response.getPrice()))
                .andExpect(jsonPath("itemName").value(response.getItemName()))
                .andDo(document("refundInfo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/refund - success")
    public void refundPayment() throws Exception {
        //given
        //when
        doNothing().when(paymentService).refundPayment(any(VerifiedRefunder.class));

        //then
        mockMvc.perform(post("/payments/refund")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer {refundAccessToken}"))
                .andExpect(status().isOk())
                .andDo(document("refundPayment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    private void validInterceptorAndArgumentResolverLoginMemberMocking(Member member) {
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.resolveArgument(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new LoginMember(1L, member.getEmail()));
    }

    private void validInterceptorAndArgumentResolverVerifiedRefunderMocking(VerifiedRefunder verifiedRefunder) {
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.resolveArgument(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new VerifiedRefunder(verifiedRefunder.getMerchantUid()));
    }
}
