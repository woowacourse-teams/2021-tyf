package com.example.tyfserver.payment.controller;

import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.dto.PaymentSaveRequest;
import com.example.tyfserver.payment.dto.PaymentSaveResponse;
import com.example.tyfserver.payment.exception.PaymentSaveRequestException;
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

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentController.class)
@AutoConfigureRestDocs
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    PaymentService paymentService;

    @MockBean
    AuthenticationArgumentResolver authenticationArgumentResolver;
    @MockBean
    AuthenticationInterceptor authenticationInterceptor;

    @Test
    @DisplayName("/payments - success")
    public void createPayment() throws Exception {
        //given
        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1000L, "test@test.com", "test");
        PaymentSaveResponse paymentSaveResponse = new PaymentSaveResponse(new Payment(1L, paymentSaveRequest.getAmount(),
                paymentSaveRequest.getEmail(), paymentSaveRequest.getPageName()));


        //when
        when(paymentService.createPayment(any(PaymentSaveRequest.class))).thenReturn(
                paymentSaveResponse
        );

        //then
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("merchantUid").value(1L))
                .andDo(document("createPayment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/payments - 회원을 찾을 수 없음")
    public void createPaymentMemberNotFoundFailed() throws Exception {
        //given
        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1000L, "test@test.com", "test");

        //when
        doThrow(new MemberNotFoundException()).when(paymentService).createPayment(any(PaymentSaveRequest.class));

        //then
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentSaveRequest)))
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
        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1000L, "  ", "test");

        //when
        //then
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentSaveRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(PaymentSaveRequestException.ERROR_CODE))
                .andDo(document("createPaymentRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }
}
