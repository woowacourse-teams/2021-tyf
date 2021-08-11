package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.AccountCancelRequest;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.admin.service.AdminService;
import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.config.RefundAuthenticationArgumentResolver;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @DisplayName("/admin/approve/{memberId}/account- success")
    public void approveAccount() throws Exception {
        //given
        doNothing().when(adminService).approveAccount(Mockito.anyLong());
        //when
        //then
        mockMvc.perform(post("/admin/approve/1/account")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("approveAccount",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/admin/cancel/{memberId}/account- success")
    public void cancelAccount() throws Exception {
        //given
        AccountCancelRequest accountCancelRequest = new AccountCancelRequest("테스트취소사유");
        //when
        doNothing().when(adminService).rejectAccount(Mockito.anyLong(), Mockito.any());
        //then
        mockMvc.perform(post("/admin/cancel/1/account")
                .content(objectMapper.writeValueAsString(accountCancelRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("cancelAccount",
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
}