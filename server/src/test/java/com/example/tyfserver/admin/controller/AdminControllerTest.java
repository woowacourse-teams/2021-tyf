package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.service.AdminService;
import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
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

import java.time.LocalDateTime;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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

    private void validInterceptorAndArgumentResolverMocking() {
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
    }
}