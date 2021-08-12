package com.example.tyfserver.banner.controller;

import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.exception.AuthorizationHeaderNotFoundException;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.banner.dto.BannerRequest;
import com.example.tyfserver.banner.dto.BannerResponse;
import com.example.tyfserver.banner.exception.BannerRequestException;
import com.example.tyfserver.banner.service.BannerService;
import com.example.tyfserver.member.domain.MemberTest;
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

import java.util.Arrays;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BannerController.class)
@AutoConfigureRestDocs
class BannerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BannerService bannerService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;

    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;


    @Test
    @DisplayName("/banners/me - success")
    public void getBanners() throws Exception {
        //given
        //when
        when(bannerService.getBanners(Mockito.any(LoginMember.class)))
                .thenReturn(Arrays.asList(
                        new BannerResponse(new Banner(1L, "imageUrl1", MemberTest.testMember())),
                        new BannerResponse(new Banner(2L, "imageUrl2", MemberTest.testMember()))
                ));
        validInterceptorAndArgumentResolverMocking();
        //then
        mockMvc.perform(get("/banners/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..id").exists())
                .andExpect(jsonPath("$..imageUrl").exists())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].imageUrl").value("imageUrl1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].imageUrl").value("imageUrl2"))
                .andDo(document("getBanners",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/banners/me - 유효하지 않은 토큰")
    public void getBannersInvalidTokenFailed() throws Exception {
        //given
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(get("/banners/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("getBannersInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/banners/me - Authorization 헤더를 찾을 수 없음")
    public void getBannersHeaderNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(get("/banners/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("getBannersHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/banners/ - success")
    public void createBanners() throws Exception {
        //given
        BannerRequest request = new BannerRequest("imageUrl");
        //when
        when(bannerService.createBanner(Mockito.any(), Mockito.any()))
                .thenReturn(1L);
        validInterceptorAndArgumentResolverMocking();
        //then
        mockMvc.perform(post("/banners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("createBanners",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/banners/ - 회원을 찾을 수 없음")
    public void createBannersMemberNotFoundFailed() throws Exception {
        //given
        BannerRequest request = new BannerRequest("imageUrl");
        //when
        doThrow(new MemberNotFoundException()).when(bannerService).createBanner(Mockito.any(), Mockito.any());
        validInterceptorAndArgumentResolverMocking();
        //then
        mockMvc.perform(post("/banners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("createBannersMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/banners/ - 유효하지 않은 Request")
    public void createBannersRequestFailed() throws Exception {
        //given
        BannerRequest request = new BannerRequest(" ");
        //when
        validInterceptorAndArgumentResolverMocking();
        //then
        mockMvc.perform(post("/banners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(BannerRequestException.ERROR_CODE))
                .andDo(document("createBannersRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/banners/ - Authorization 헤더를 찾을 수 없음")
    public void createBannersHeaderNotFoundFailed() throws Exception {
        //given
        BannerRequest request = new BannerRequest("imageUrl");
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(post("/banners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("createBannersHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/banners/ - 유효하지 않은 토큰")
    public void createBannersInvalidTokenFailed() throws Exception {
        //given
        BannerRequest request = new BannerRequest("imageUrl");
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(post("/banners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("createBannersInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    private void validInterceptorAndArgumentResolverMocking() {
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.resolveArgument(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new LoginMember(1L, "email"));
    }
}