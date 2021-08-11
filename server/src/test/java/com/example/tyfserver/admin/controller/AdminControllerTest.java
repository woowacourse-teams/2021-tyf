package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.exception.InvalidAdminException;
import com.example.tyfserver.admin.service.AdminService;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.service.AuthenticationService;
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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
    private AuthenticationService authenticationService;

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
