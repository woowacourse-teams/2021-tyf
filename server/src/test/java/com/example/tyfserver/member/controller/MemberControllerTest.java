package com.example.tyfserver.member.controller;

import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.exception.AuthorizationHeaderNotFoundException;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.DuplicatedNicknameException;
import com.example.tyfserver.member.exception.DuplicatedPageNameException;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    AuthenticationArgumentResolver authenticationArgumentResolver;
    @MockBean
    AuthenticationInterceptor authenticationInterceptor;
    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("/members/validate/pageName - success")
    public void validatePageName() throws Exception {
        //given
        PageNameValidationRequest request = new PageNameValidationRequest("pagename");

        //when
        doNothing().when(memberService).validatePageName(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/pageName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("/members/validate/pageName - 유효하지 않은 request")
    public void validatePageNameRequestFailed() throws Exception {
        //given
        PageNameValidationRequest request = new PageNameValidationRequest("INVALID");

        //when
        doNothing().when(memberService).validatePageName(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/pageName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("member-002"))
        ;
    }

    @Test
    @DisplayName("/members/validate/pageName - 중복된 pageName")
    public void validatePageNametDuplicatedFailed() throws Exception {
        //given
        PageNameValidationRequest request = new PageNameValidationRequest("pagename");

        //when
        doThrow(new DuplicatedPageNameException()).when(memberService).validatePageName(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/pageName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("member-003"))
        ;
    }

    @Test
    @DisplayName("/members/validate/nickname - success")
    public void validateNickname() throws Exception {
        //given
        NicknameValidationRequest request = new NicknameValidationRequest("nickname");

        //when
        doNothing().when(memberService).validateNickname(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("/members/validate/nickname - 유효하지 않은 request")
    public void validateNicknameRequestFailed() throws Exception {
        //given
        NicknameValidationRequest request = new NicknameValidationRequest("!@#INVALID");

        //when
        doNothing().when(memberService).validateNickname(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("member-004"))
        ;
    }

    @Test
    @DisplayName("/members/validate/nickname - 중복된 nickname")
    public void validateNicknametDuplicatedFailed() throws Exception {
        //given
        NicknameValidationRequest request = new NicknameValidationRequest("nickname");

        //when
        doThrow(new DuplicatedNicknameException()).when(memberService).validateNickname(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("member-005"))
        ;
    }

    @Test
    @DisplayName("/members/{pageName} - success")
    public void memberInfo() throws Exception {
        //given
        MemberResponse response = new MemberResponse("email", "nickname", "pagename");
        //when
        when(memberService.findMember(Mockito.anyString())).thenReturn(response);
        //then
        mockMvc.perform(get("/members/pagename")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("email"))
                .andExpect(jsonPath("nickname").value("nickname"))
                .andExpect(jsonPath("pageName").value("pagename"))
        ;
    }

    @Test
    @DisplayName("/members/{pageName} - 회원을 찾을 수 없음")
    public void memberInfoMemberNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new MemberNotFoundException()).when(memberService).findMember(Mockito.anyString());
        //then
        mockMvc.perform(get("/members/pagename")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("member-001"))
        ;
    }

    @Test
    @DisplayName("/members/me - success")
    public void memberDetail() throws Exception {
        //given
        MemberDetailResponse response = new MemberDetailResponse("email", "nickname", "pagename");
        //when
        when(memberService.findMemberDetail(Mockito.anyLong())).thenReturn(response);
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.resolveArgument(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(new LoginMember(1L, "email"));
        //then
        mockMvc.perform(get("/members/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("email"))
                .andExpect(jsonPath("nickname").value("nickname"))
                .andExpect(jsonPath("pageName").value("pagename"))
        ;
    }

    @Test
    @DisplayName("/members/me - 회원을 찾을 수 없음")
    public void memberDetailMemberNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new MemberNotFoundException()).when(memberService).findMemberDetail(Mockito.anyLong());
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.resolveArgument(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(new LoginMember(1L, "email"));
        //then
        mockMvc.perform(get("/members/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("member-001"))
        ;
    }

    @Test
    @DisplayName("/members/me - authorization header not found")
    public void memberDetailHeaderNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(get("/members/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("auth-001"))
        ;
    }

    @Test
    @DisplayName("/members/me - invalid token")
    public void memberDetailInvalidTokenFailed() throws Exception {
        //given
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(get("/members/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("auth-002"))
        ;
    }

    @Test
    @DisplayName("/members/me/point - success")
    public void memberPoint() throws Exception {
        //given
        PointResponse response = new PointResponse(1000L);
        //when
        when(memberService.findMemberPoint(Mockito.anyLong())).thenReturn(response);
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.resolveArgument(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(new LoginMember(1L, "email"));
        //then
        mockMvc.perform(get("/members/me/point")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("point").value(1000L))
        ;
    }

    @Test
    @DisplayName("/members/me/point - 회원을 찾을 수 없음")
    public void memberPointMemberNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new MemberNotFoundException()).when(memberService).findMemberPoint(Mockito.anyLong());
        when(authenticationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(authenticationArgumentResolver.resolveArgument(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(new LoginMember(1L, "email"));
        //then
        mockMvc.perform(get("/members/me/point")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("member-001"))
        ;
    }

    @Test
    @DisplayName("/members/me/point - authorization header not found")
    public void memberPointHeaderNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(get("/members/me/point")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("auth-001"))
        ;
    }

    @Test
    @DisplayName("/members/me/point - invalid token")
    public void memberPointInvalidTokenFailed() throws Exception {
        //given
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(get("/members/me/point")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("auth-002"))
        ;
    }

    @Test
    @DisplayName("/members/curations - success")
    public void curations() throws Exception {
        //given
        //when
        when(memberService.findCurations()).thenReturn(
                Arrays.asList(new CurationsResponse("nickname1", 1000L, "pagename1"),
                        new CurationsResponse("nickname2", 2000L, "pagename2"))
        );
        //then
        mockMvc.perform(get("/members/curations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..nickname").exists())
                .andExpect(jsonPath("$..donationAmount").exists())
                .andExpect(jsonPath("$..pageName").exists())
                .andExpect(jsonPath("$[0].nickname").value("nickname1"))
                .andExpect(jsonPath("$[0].donationAmount").value(1000L))
                .andExpect(jsonPath("$[0].pageName").value("pagename1"))
        ;
    }
}