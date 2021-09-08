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
    private static final String IMP_ID = "imp_id";
    private static final String MODULE = "테스트모듈";
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
        DonationResponse response = new DonationResponse(1L, "name", "message", amount, LocalDateTime.now());
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
                .andExpect(jsonPath("createdAt").value(response.getCreatedAt().toString()))
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

    // todo 제거
//    @Test
//    @DisplayName("/donations - 실 결제 정보 상태가 PAID가 아닐경우")
//    public void createDonationFailedStatusNotPaid() throws Exception {
//        //given
//        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_ID, MERCHANT_UID);
//        //when
//        doThrow(IllegalPaymentInfoException.from(ERROR_CODE_NOT_PAID, "테스트모듈"))
//                .when(donationService).createDonation(any(DonationRequest.class), anyLong());
//
//        //then
//        mockMvc.perform(post("/donations")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("errorCode").value(ERROR_CODE_NOT_PAID))
//                .andDo(document("createDonationFailedStatusNotPaid",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())))
//        ;
//    }

//    @Test
//    @DisplayName("/donations - 실 결제 정보의 id와 저장된 결제정보의 id가 일치하지 않을 경우")
//    public void createDonationFailedInvalidId() throws Exception {
//        //given
//        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_ID, MERCHANT_UID);
//        //when
//        doThrow(IllegalPaymentInfoException.from(ERROR_CODE_INVALID_MERCHANT_ID, MODULE))
//                .when(donationService).createDonation(any(DonationRequest.class), anyLong());
//
//        //then
//        mockMvc.perform(post("/donations")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("errorCode").value(ERROR_CODE_INVALID_MERCHANT_ID))
//                .andDo(document("createDonationFailedStatusInvalidId",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())))
//        ;
//    }

//    @Test
//    @DisplayName("/donations - 실 결제 정보의 Amount와 저장된 결제정보의 Amount가 일치하지 않을 경우")
//    public void createDonationFailedInvalidAmount() throws Exception {
//        //given
//        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_ID, MERCHANT_UID);
//        //when
//        doThrow(IllegalPaymentInfoException.from(ERROR_CODE_INVALID_AMOUNT, MODULE))
//                .when(donationService).createDonation(any(DonationRequest.class), anyLong());
//
//        //then
//        mockMvc.perform(post("/donations")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("errorCode").value(ERROR_CODE_INVALID_AMOUNT))
//                .andDo(document("createDonationFailedInvalidAmount",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())))
//        ;
//    }

//    @Test
//    @DisplayName("/donations - 실 결제 정보의 PageName과 저장된 결제정보의 PageName이 일치하지 않을 경우")
//    public void createDonationFailedInvalidPageName() throws Exception {
//        //given
//        PaymentCompleteRequest request = new PaymentCompleteRequest(IMP_ID, MERCHANT_UID);
//        //when
//        doThrow(IllegalPaymentInfoException.from(ERROR_CODE_INVALID_CREATOR, MODULE))
//                .when(donationService).createDonation(any(DonationRequest.class), anyLong());
//
//        //then
//        mockMvc.perform(post("/donations")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("errorCode").value(ERROR_CODE_INVALID_CREATOR))
//                .andDo(document("createDonationFailedInvalidPageName",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())))
//        ;
//    }

    @Test
    @DisplayName("/donations/{donationId}/messages - success")
    public void addDonationMessage() throws Exception {
        //given
        DonationMessageRequest request = new DonationMessageRequest("name", "message", true);
        //when
        doNothing().when(donationService).addMessageToDonation(anyLong(), any(DonationMessageRequest.class));
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
                .when(donationService).addMessageToDonation(anyLong(), any(DonationMessageRequest.class));
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
        DonationMessageRequest request = new DonationMessageRequest("", "message", true);
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
        when(donationService.findMyDonations(any(), any()))
                .thenReturn(Arrays.asList(
                        new DonationResponse(1L, "name1", "message1", 1000L, LocalDateTime.now()),
                        new DonationResponse(2L, "name2", "message2", 2000L, LocalDateTime.now())
                ));
        when(authenticationInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(false);
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
        doThrow(new MemberNotFoundException()).when(donationService).findMyDonations(any(), any());
        when(authenticationInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(false);
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
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(any(), any(), any());
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
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(any(), any(), any());
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
        when(donationService.findPublicDonations(anyString()))
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
