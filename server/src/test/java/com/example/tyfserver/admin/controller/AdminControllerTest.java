package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.admin.exception.InvalidAdminException;
import com.example.tyfserver.admin.service.AdminService;
import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.config.RefundAuthenticationArgumentResolver;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.dto.TokenResponse;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AdminController.class)
@AutoConfigureRestDocs
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminService adminService;

    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;

    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;

    @MockBean
    private RefundAuthenticationArgumentResolver refundAuthenticationArgumentResolver;

    @Test
    @DisplayName("/admin/account/approve/{memberId} - success")
    public void approveAccount() throws Exception {
        //given
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        doNothing().when(adminService).approveAccount(Mockito.anyLong());
        //when
        //then
        mockMvc.perform(post("/admin/account/approve/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("approveAccount",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/admin/account/approve/{memberId} - fail - member not found")
    public void approveAccountFailWhenMemberNotFound() throws Exception {
        //when
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        doThrow(new MemberNotFoundException()).when(adminService).approveAccount(anyLong());
        //then
        mockMvc.perform(post("/admin/account/approve/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("approveAccountFailWhenMemberNotFound",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/admin/account/reject/{memberId} - success")
    public void rejectAccount() throws Exception {
        //given
        AccountRejectRequest accountRejectRequest = new AccountRejectRequest("테스트취소사유");
        //when
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        doNothing().when(adminService).rejectAccount(Mockito.anyLong(), Mockito.any());
        //then
        mockMvc.perform(post("/admin/account/reject/1")
                .content(objectMapper.writeValueAsString(accountRejectRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("rejectAccount",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/admin/account/reject/{memberId}- fail - member not found")
    public void approveRejectFailWhenMemberNotFound() throws Exception {
        //when
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        doThrow(new MemberNotFoundException()).when(adminService).rejectAccount(anyLong(), any());

        //then
        mockMvc.perform(post("/admin/account/reject/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("rejectAccountFailWhenMemberNotFound",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/admin/list/account- success")
    public void requestingAccounts() throws Exception {
        //given
        List<RequestingAccountResponse> responses = new ArrayList<>();
        responses.add(new RequestingAccountResponse(1L, "test1@test.com", "nickname1", "pagename1", "accountholder1",
                "1234-1234-12341", "bank", "https://test.test.png"));
        responses.add(new RequestingAccountResponse(2L, "test2@test.com", "nickname2", "pagename2", "accountholder2",
                "1234-1234-12342", "bank", "https://test.test.png"));

        //when
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(adminService.findRequestingAccounts()).thenReturn(responses);
        //then
        mockMvc.perform(get("/admin/list/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..memberId").exists())
                .andExpect(jsonPath("$..nickname").exists())
                .andExpect(jsonPath("$..accountHolder").exists())
                .andExpect(jsonPath("$..accountNumber").exists())
                .andExpect(jsonPath("$..bank").exists())
                .andExpect(jsonPath("$..bankbookImageUrl").exists())
                .andExpect(jsonPath("$[0].accountNumber").value("1234-1234-12341"))
                .andDo(document("requestingAccounts",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
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

    @Test
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
}