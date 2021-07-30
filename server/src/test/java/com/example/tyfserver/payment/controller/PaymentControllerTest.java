package com.example.tyfserver.payment.controller;

import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.payment.dto.PaymentCancelRequest;
import com.example.tyfserver.payment.dto.PaymentCancelResponse;
import com.example.tyfserver.payment.dto.PaymentPendingRequest;
import com.example.tyfserver.payment.dto.PaymentPendingResponse;
import com.example.tyfserver.payment.exception.PaymentCancelRequestException;
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

import java.util.HashMap;
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
    private AuthenticationArgumentResolver authenticationArgumentResolver;

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
    @DisplayName("/payments/cancel - success")
    public void cancelPayment() throws Exception {
        //given
        PaymentCancelRequest cancelRequest = new PaymentCancelRequest(MERCHANT_UID);
        PaymentCancelResponse cancelResponse = new PaymentCancelResponse(MERCHANT_UID);

        //when
        when(paymentService.cancelPayment(any(PaymentCancelRequest.class)))
                .thenReturn(cancelResponse);

        //then
        mockMvc.perform(post("/payments/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cancelRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("merchantUid").value(MERCHANT_UID.toString()))
                .andDo(document("cancelPayment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/cancel - 회원을 찾을 수 없음")
    public void cancelPaymentMemberNotFoundFailed() throws Exception {
        //given
        PaymentCancelRequest cancelRequest = new PaymentCancelRequest(MERCHANT_UID);

        //when
        doThrow(new MemberNotFoundException())
                .when(paymentService)
                .cancelPayment(any(PaymentCancelRequest.class));

        //then
        mockMvc.perform(post("/payments/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cancelRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("cancelPaymentMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments/cancel - 유효하지 않은 Request")
    public void cancelPaymentRequestFailed() throws Exception {
        //given
        HashMap<Object, Object> map = new HashMap<>() {{
            put("merchantUid", "UUID형식이 아닌 문자열");
        }};
        PaymentCancelRequest cancelRequest = new PaymentCancelRequest(UUID.randomUUID());


        //when
        //then
        mockMvc.perform(post("/payments/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(PaymentCancelRequestException.ERROR_CODE))
                .andDo(document("cancelPaymentRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }
}
