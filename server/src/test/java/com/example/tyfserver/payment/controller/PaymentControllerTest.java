package com.example.tyfserver.payment.controller;

import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.dto.VerifiedRefundRequest;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.payment.dto.PaymentPendingRequest;
import com.example.tyfserver.payment.dto.PaymentPendingResponse;
import com.example.tyfserver.payment.exception.PaymentPendingRequestException;
import com.example.tyfserver.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentController.class)
@AutoConfigureRestDocs
public class PaymentControllerTest {

    private static final UUID MERCHANT_UID = UUID.randomUUID();

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

    @Test
    @DisplayName("/payments - success")
    public void createPayment() throws Exception {
        //given
        PaymentPendingRequest pendingRequest = new PaymentPendingRequest(1000L, "test@test.com", "test");
        PaymentPendingResponse pendingResponse = new PaymentPendingResponse(MERCHANT_UID);

        //when
        when(paymentService.createPayment(any(PaymentPendingRequest.class)))
                .thenReturn(pendingResponse);

        //then
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pendingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("merchantUid").value(MERCHANT_UID.toString()))
                .andDo(document("createPayment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments - 회원을 찾을 수 없음")
    public void createPaymentMemberNotFoundFailed() throws Exception {
        //given
        PaymentPendingRequest pendingRequest = new PaymentPendingRequest(1000L, "test@test.com", "test");

        //when
        doThrow(new MemberNotFoundException())
                .when(paymentService)
                .createPayment(any(PaymentPendingRequest.class));

        //then
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pendingRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("createPaymentMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }


    @Test
    @DisplayName("/payments - 유효하지 않은 Request")
    public void createPaymentRequestFailed() throws Exception {
        //given
        PaymentPendingRequest pendingRequest = new PaymentPendingRequest(1000L, "  ", "test");

        //when
        //then
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pendingRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(PaymentPendingRequestException.ERROR_CODE))
                .andDo(document("createPaymentRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/refund - success")
    public void refundPayment() throws Exception {
        //given
        //when
        doNothing().when(paymentService).refundPayment(any(VerifiedRefundRequest.class));

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
}
