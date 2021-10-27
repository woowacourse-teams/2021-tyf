package com.example.tyfserver.donation.controller;

import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.exception.AuthorizationHeaderNotFoundException;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.exception.DonationMessageRequestException;
import com.example.tyfserver.donation.exception.DonationNotFoundException;
import com.example.tyfserver.donation.exception.DonationRequestException;
import com.example.tyfserver.donation.service.DonationService;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.exception.WrongDonationOwnerException;
import com.example.tyfserver.payment.dto.PaymentCompleteRequest;
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
import java.util.UUID;

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

    private static final String MERCHANT_UID = UUID.randomUUID().toString();
    private static final String PAGENAME = "pagename";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;
    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;
    @MockBean
    private DonationService donationService;

    private void validInterceptorAndArgumentResolverMocking() {
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.resolveArgument(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new LoginMember(1L, "email"));
    }

    @Test
    @DisplayName("/donations - success")
    public void createDonation() throws Exception {
        //given
        long amount = 1000L;
        DonationRequest request = new DonationRequest(PAGENAME, amount);
        DonationResponse response = new DonationResponse(1L, "name", "message", amount, LocalDateTime.now(), "pagename");
        //when
        validInterceptorAndArgumentResolverMocking();
        when(donationService.createDonation(any(DonationRequest.class), anyLong()))
                .thenReturn(response);
        //then
        mockMvc.perform(post("/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("donationId").value(response.getDonationId()))
                .andExpect(jsonPath("name").value(response.getName()))
                .andExpect(jsonPath("message").value(response.getMessage()))
                .andExpect(jsonPath("amount").value(response.getAmount()))
                .andDo(document("createDonation",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations - 회원을 찾을 수 없음")
    public void createDonationMemberNotFoundFailed() throws Exception {
        //given
        DonationRequest request = new DonationRequest("invalid", 1000L);
        //when
        validInterceptorAndArgumentResolverMocking();
        doThrow(new MemberNotFoundException()).when(donationService).createDonation(any(DonationRequest.class), anyLong());
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
        PaymentCompleteRequest request = new PaymentCompleteRequest("  ", MERCHANT_UID);
        //when
        validInterceptorAndArgumentResolverMocking();
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
        DonationMessageRequest request = new DonationMessageRequest("message", true);
        //when
        validInterceptorAndArgumentResolverMocking();
        doNothing().when(donationService).addMessageToDonation(anyLong(), anyLong(), any(DonationMessageRequest.class));
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
        DonationMessageRequest request = new DonationMessageRequest("message", true);
        //when
        validInterceptorAndArgumentResolverMocking();
        doThrow(new DonationNotFoundException())
                .when(donationService).addMessageToDonation(anyLong(), anyLong(), any(DonationMessageRequest.class));
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
        DonationMessageRequest request = new DonationMessageRequest("", true);
        //when
        validInterceptorAndArgumentResolverMocking();
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
    @DisplayName("/donations/{donationId}/messages - Donation의 주인이 아닌데 메세지 수정 요청을 한 경우")
    public void addDonationMessageNotOwner() throws Exception {
        //given
        DonationMessageRequest request = new DonationMessageRequest("message", true);
        //when
        validInterceptorAndArgumentResolverMocking();
        doThrow(new WrongDonationOwnerException())
                .when(donationService).addMessageToDonation(anyLong(), anyLong(), any(DonationMessageRequest.class));
        //then
        mockMvc.perform(post("/donations/1/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(WrongDonationOwnerException.ERROR_CODE))
                .andDo(document("addDonationMessageNotOwner",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/me - success")
    public void myDonations() throws Exception {
        //given
        //when
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(false);
        when(donationService.findMyDonations(any(), anyLong()))
                .thenReturn(Arrays.asList(
                        new DonationResponse(1L, "name1", "message1", 1000L, LocalDateTime.now(), "pagename"),
                        new DonationResponse(2L, "name2", "message2", 2000L, LocalDateTime.now(), "pagename")
                ));
        //then
        mockMvc.perform(get("/donations/me")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cursorId", "0"))
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
    public void myDonationsMemberNotFoundFailed() throws Exception {
        //given
        //when
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(false);
        doThrow(new MemberNotFoundException()).when(donationService).findMyDonations(any(), anyLong());
        //then
        mockMvc.perform(get("/donations/me")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cursorId", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("totalDonationsMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/me - Authorization 헤더를 찾을 수 없음")
    public void myDonationsHeaderNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(any(), any(), any());
        //then
        mockMvc.perform(get("/donations/me")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cursorId", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("totalDonationsHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/donations/me - 유효하지 않은 토큰")
    public void myDonationsInvalidTokenFailed() throws Exception {
        //given
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(any(), any(), any());
        //then
        mockMvc.perform(get("/donations/me")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cursorId", "0"))
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
        when(donationService.findPublicDonations(anyString()))
                .thenReturn(Arrays.asList(
                        new DonationResponse(1L, "default", "defaultMessage1", 1000L, LocalDateTime.now(), "pagename"),
                        new DonationResponse(2L, "default2", "defaultMessage2", 2000L, LocalDateTime.now(), "pagename")
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
        doThrow(new MemberNotFoundException()).when(donationService).findPublicDonations(anyString());
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
