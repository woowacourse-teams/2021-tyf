package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.dto.ExchangeRejectRequest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.exception.InvalidAdminException;
import com.example.tyfserver.admin.service.AdminService;
import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.member.exception.MemberNotFoundException;
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

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//todo: 토큰을 통한 Admin 생성이 이루어지면 해당 케이스들 추가해야함
@WebMvcTest(controllers = AdminController.class)
@AutoConfigureRestDocs
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdminService adminService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;
    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;
    @MockBean
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("정산 신청 목록 조회")
    public void exchangeList() throws Exception {
        when(adminService.exchangeList())
                .thenReturn(singletonList(
                        new ExchangeResponse(10000L, "123-123", "nickname", "pagename", LocalDateTime.now())
                ));

        mockMvc.perform(get("/admin/list/exchange")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].exchangeAmount").value(10000L))
                .andExpect(jsonPath("$[0].accountNumber").value("123-123"))
                .andExpect(jsonPath("$[0].nickname").value("nickname"))
                .andExpect(jsonPath("$[0].pageName").value("pagename"))
                .andDo(print())
                .andDo(document("exchangeList",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("정산 승인")
    public void approveExchange() throws Exception {
        String pageName = "pagename";
        doNothing().when(adminService).approveExchange(anyString());

        mockMvc.perform(post("/admin/exchange/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pageName)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("approveExchange",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("정산 승인 회원을 찾을 수 없음")
    public void approveExchangeMemberNotFound() throws Exception {
        doThrow(new MemberNotFoundException()).when(adminService).approveExchange(anyString());
        String pageName = "pagename";

        mockMvc.perform(post("/admin/exchange/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pageName)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(print())
                .andDo(document("approveExchangeMemberNotFound",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("정산 거절")
    public void rejectExchange() throws Exception {
        ExchangeRejectRequest request = new ExchangeRejectRequest("pagename", "no reason just fun");
        doNothing().when(adminService).approveExchange(anyString());

        mockMvc.perform(post("/admin/exchange/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("rejectExchange",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("정산 승인 회원을 찾을 수 없음")
    public void rejectExchangeMemberNotFound() throws Exception {
        ExchangeRejectRequest request = new ExchangeRejectRequest("pagename", "no reason just fun");
        doThrow(new MemberNotFoundException()).when(adminService).approveExchange(anyString());

        mockMvc.perform(post("/admin/exchange/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(print())
                .andDo(document("rejectExchangeMemberNotFound",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @DisplayName("/admin/login - success")
    public void login() throws Exception {
        //given
        when(adminService.login(Mockito.any()))
                .thenReturn(new TokenResponse("token"));

        //when //then
        mockMvc.perform(post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AdminLoginRequest("id", "password"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andDo(print())
                .andDo(document("adminLogin",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @DisplayName("/admin/login - UnregisteredAdminMember Failed")
    public void loginUnregisteredMemberFailed() throws Exception {
        //given
        doThrow(new InvalidAdminException())
                .when(adminService).login(Mockito.any());

        //when //then
        mockMvc.perform(post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AdminLoginRequest("id", "password"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidAdminException.ERROR_CODE))
                .andDo(print())
                .andDo(document("adminLoginUnregisteredMemberFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    private void validInterceptorAndArgumentResolverMocking() {
        when(authenticationInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(any())).thenReturn(true);
    }
}
