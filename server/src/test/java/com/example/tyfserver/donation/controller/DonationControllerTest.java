package com.example.tyfserver.donation.controller;

import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.exception.AuthorizationHeaderNotFoundException;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.exception.DonationMessageRequestException;
import com.example.tyfserver.donation.exception.DonationNotFoundException;
import com.example.tyfserver.donation.exception.DonationRequestException;
import com.example.tyfserver.donation.service.DonationService;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.payment.dto.PaymentRequest;
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

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DonationController.class)
@AutoConfigureRestDocs
class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    AuthenticationArgumentResolver authenticationArgumentResolver;
    @MockBean
    AuthenticationInterceptor authenticationInterceptor;
    @MockBean
    private DonationService donationService;

    @Test
    @DisplayName("/donations - success")
    public void createDonation() throws Exception {
        //given
        DonationRequest request = new DonationRequest("pagename", 1000L);
        DonationResponse response = new DonationResponse(1L, "name", "message", 1000L, LocalDateTime
            .now());
        //when
        when(donationService.createDonation(Mockito.any(PaymentRequest.class)))
                .thenReturn(response);
        //then
        mockMvc.perform(post("/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("donationId").value(1L))
                .andExpect(jsonPath("name").value("name"))
                .andExpect(jsonPath("message").value("message"))
                .andExpect(jsonPath("amount").value(1000L))
                .andDo(document("createDonation",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations - 회원을 찾을 수 없음")
    public void createDonationMemberNotFoundFailed() throws Exception {
        //given
        DonationRequest request = new DonationRequest("pagename", 1000L);
        //when
        doThrow(new MemberNotFoundException()).when(donationService).createDonation(Mockito.any(PaymentRequest.class));
        //then
        mockMvc.perform(post("/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("createDonationMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations - 유효하지 않은 request")
    public void createDonationRequestFailed() throws Exception {
        //given
        PaymentRequest request = new PaymentRequest("  ", 1L);
        //when
        //then
        mockMvc.perform(post("/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(DonationRequestException.ERROR_CODE))
                .andDo(document("createDonationRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/{donationId}/messages - success")
    public void addDonationMessage() throws Exception {
        //given
        DonationMessageRequest request = new DonationMessageRequest("name", "message", true);
        //when
        doNothing().when(donationService).addMessageToDonation(Mockito.anyLong(), Mockito.any(DonationMessageRequest.class));
        //then
        mockMvc.perform(post("/donations/1/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("addDonationMessage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/{donationId}/messages - 후원을 찾을 수 없음")
    public void addDonationMessageDonationNotFoundFailed() throws Exception {
        //given
        DonationMessageRequest request = new DonationMessageRequest("name", "message", true);
        //when
        doThrow(new DonationNotFoundException())
                .when(donationService).addMessageToDonation(Mockito.anyLong(), Mockito.any(DonationMessageRequest.class));
        //then
        mockMvc.perform(post("/donations/1/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(DonationNotFoundException.ERROR_CODE))
                .andDo(document("addDonationMessageDonationNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/{donationId}/messages - 유효하지 않은 request")
    public void addDonationMessageRequestFailed() throws Exception {
        //given
        DonationMessageRequest request = new DonationMessageRequest("a", "message", true);
        //when
        //then
        mockMvc.perform(post("/donations/1/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(DonationMessageRequestException.ERROR_CODE))
                .andDo(document("addDonationMessageRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/me - success")
    public void totalDonations() throws Exception {
        //given
        //when
        when(donationService.findMyDonations(Mockito.any(), Mockito.any()))
                .thenReturn(Arrays.asList(
                        new DonationResponse(1L, "name1", "message1", 1000L, LocalDateTime.now()),
                        new DonationResponse(2L, "name2", "message2", 2000L, LocalDateTime.now())
                ));
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(false);
        //then
        mockMvc.perform(get("/donations/me")
                .contentType(MediaType.APPLICATION_JSON)
                .param("size", "2")
                .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..donationId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..message").exists())
                .andExpect(jsonPath("$..amount").exists())
                .andExpect(jsonPath("$[1].amount").value(2000L))
                .andDo(document("totalDonations",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/me - 회원을 찾을 수 없음")
    public void totalDonationsMemberNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new MemberNotFoundException()).when(donationService).findMyDonations(Mockito.any(), Mockito.any());
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(false);
        //then
        mockMvc.perform(get("/donations/me")
                .contentType(MediaType.APPLICATION_JSON)
                .param("size", "2")
                .param("page", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("totalDonationsMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/me - Authorization 헤더를 찾을 수 없음")
    public void totalDonationsHeaderNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(get("/donations/me")
                .contentType(MediaType.APPLICATION_JSON)
                .param("size", "2")
                .param("page", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("totalDonationsHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/me - 유효하지 않은 토큰")
    public void totalDonationsInvalidTokenFailed() throws Exception {
        //given
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(get("/donations/me")
                .contentType(MediaType.APPLICATION_JSON)
                .param("size", "2")
                .param("page", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("totalDonationsInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/public/{pageName} - success")
    public void publicDonations() throws Exception {
        //given
        //when
        when(donationService.findPublicDonations(Mockito.anyString()))
                .thenReturn(Arrays.asList(
                        new DonationResponse(1L, "default", "defaultMessage1", 1000L, LocalDateTime.now()),
                        new DonationResponse(2L, "default2", "defaultMessage2", 2000L, LocalDateTime.now())
                ));
        //then
        mockMvc.perform(get("/donations/public/pagename")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..donationId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..message").exists())
                .andExpect(jsonPath("$..amount").exists())
                .andExpect(jsonPath("$..createdAt").exists())
                .andExpect(jsonPath("$[1].amount").value(2000L))
                .andDo(document("publicDonations",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/public/{pageName} - 회원을 찾을 수 없음")
    public void publicDonationsMemberNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new MemberNotFoundException()).when(donationService).findPublicDonations(Mockito.anyString());
        //then
        mockMvc.perform(get("/donations/public/pagename")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("publicDonationsMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }
}
