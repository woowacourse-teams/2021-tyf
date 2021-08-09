package com.example.tyfserver.member.controller;

import com.example.tyfserver.auth.config.AuthenticationArgumentResolver;
import com.example.tyfserver.auth.config.AuthenticationInterceptor;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.auth.exception.AuthorizationHeaderNotFoundException;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.common.exception.S3FileNotFoundException;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.*;
import com.example.tyfserver.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
@AutoConfigureRestDocs
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;
    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;
    @MockBean
    private MemberService memberService;
    @MockBean
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("/members/validate/pageName - success")
    public void validatePageName() throws Exception {
        //given
        PageNameRequest request = new PageNameRequest("pagename");

        //when
        doNothing().when(memberService).validatePageName(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/pageName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("validatePageName",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/validate/pageName - 유효하지 않은 request")
    public void validatePageNameRequestFailed() throws Exception {
        //given
        PageNameRequest request = new PageNameRequest("INVALID");

        //when
        doNothing().when(memberService).validatePageName(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/pageName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(PageNameValidationRequestException.ERROR_CODE))
                .andDo(print())
                .andDo(document("validatePageNameRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/validate/pageName - 중복된 pageName")
    public void validatePageNameDuplicatedFailed() throws Exception {
        //given
        PageNameRequest request = new PageNameRequest("pagename");

        //when
        doThrow(new DuplicatedPageNameException()).when(memberService).validatePageName(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/pageName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(DuplicatedPageNameException.ERROR_CODE))
                .andDo(document("validatePageNameDuplicatedFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/validate/nickname - success")
    public void validateNickname() throws Exception {
        //given
        NicknameRequest request = new NicknameRequest("nickname");

        //when
        doNothing().when(memberService).validateNickname(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("validateNickname",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/validate/nickname - 유효하지 않은 request")
    public void validateNicknameRequestFailed() throws Exception {
        //given
        NicknameRequest request = new NicknameRequest("!@#INVALID");

        //when
        doNothing().when(memberService).validateNickname(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(NicknameValidationRequestException.ERROR_CODE))
                .andDo(document("validateNicknameRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/validate/nickname - 중복된 nickname")
    public void validateNicknameDuplicatedFailed() throws Exception {
        //given
        NicknameRequest request = new NicknameRequest("nickname");

        //when
        doThrow(new DuplicatedNicknameException()).when(memberService).validateNickname(Mockito.any());
        //then
        mockMvc.perform(post("/members/validate/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(DuplicatedNicknameException.ERROR_CODE))
                .andDo(document("validateNicknameDuplicatedFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/{pageName} - success")
    public void memberInfo() throws Exception {
        //given
        MemberResponse response = new MemberResponse("email", "nickname",
                "pagename", "I am test", "profile.png");
        //when
        when(memberService.findMember(Mockito.anyString())).thenReturn(response);
        //then
        mockMvc.perform(get("/members/pagename")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("email"))
                .andExpect(jsonPath("nickname").value("nickname"))
                .andExpect(jsonPath("pageName").value("pagename"))
                .andExpect(jsonPath("bio").value("I am test"))
                .andDo(document("memberInfo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
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
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("memberInfoMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/me - success")
    public void memberDetail() throws Exception {
        //given
        MemberResponse response = new MemberResponse("email", "nickname", "pagename",
                "I am test", "profile.png");
        //when
        when(memberService.findMemberDetail(Mockito.anyLong())).thenReturn(response);
        validInterceptorAndArgumentResolverMocking();
        //then
        mockMvc.perform(get("/members/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("email"))
                .andExpect(jsonPath("nickname").value("nickname"))
                .andExpect(jsonPath("pageName").value("pagename"))
                .andExpect(jsonPath("bio").value("I am test"))
                .andDo(document("memberDetail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/me - 회원을 찾을 수 없음")
    public void memberDetailMemberNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new MemberNotFoundException()).when(memberService).findMemberDetail(Mockito.anyLong());
        validInterceptorAndArgumentResolverMocking();
        //then
        mockMvc.perform(get("/members/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("memberDetailMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
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
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("memberDetailHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
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
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("memberDetailInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/me/point - success")
    public void memberPoint() throws Exception {
        //given
        PointResponse response = new PointResponse(1000L);
        //when
        when(memberService.findMemberPoint(Mockito.anyLong())).thenReturn(response);
        validInterceptorAndArgumentResolverMocking();
        //then
        mockMvc.perform(get("/members/me/point")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("point").value(1000L))
                .andDo(document("memberPoint",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/me/point - 회원을 찾을 수 없음")
    public void memberPointMemberNotFoundFailed() throws Exception {
        //given
        //when
        doThrow(new MemberNotFoundException()).when(memberService).findMemberPoint(Mockito.anyLong());
        validInterceptorAndArgumentResolverMocking();
        //then
        mockMvc.perform(get("/members/me/point")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("memberPointMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
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
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("memberPointHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
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
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("memberPointInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/curations - success")
    public void curations() throws Exception {
        //given
        //when
        when(memberService.findCurations()).thenReturn(
                Arrays.asList(new CurationsResponse("nickname1", 1000L,
                                "pagename1", "https://cloudfront.net/profile1.png", "I am test"),
                        new CurationsResponse("nickname2", 2000L,
                                "pagename2", "https://cloudfront.net/profile2.png", "I am test"))
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
                .andExpect(jsonPath("$[0].profileImage").value("https://cloudfront.net/profile1.png"))
                .andExpect(jsonPath("$[0].bio").value("I am test"))
                .andDo(document("curations",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/validate/token - success")
    public void validateToken() throws Exception {
        //given
        TokenValidationRequest request = new TokenValidationRequest("tokenValue");
        //when
        doNothing().when(authenticationService).validateToken(Mockito.anyString());
        //then
        mockMvc.perform(post("/members/validate/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("validateToken",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/members/validate/token - Invalid Token")
    public void validateTokenInvalidTokenFailed() throws Exception {
        //given
        TokenValidationRequest request = new TokenValidationRequest("tokenValue");
        //when
        doThrow(new InvalidTokenException()).when(authenticationService).validateToken(Mockito.anyString());
        //then
        mockMvc.perform(post("/members/validate/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("validateTokenInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("POST - /members/profile - success")
    public void profile() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("profileImage", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());
        String url = "https://de56jrhz7aye2.cloudfront.net/file";

        //when
        validInterceptorAndArgumentResolverMocking();
        when(memberService.uploadProfile(Mockito.any(), Mockito.any()))
                .thenReturn(new ProfileResponse(url));

        //then
        mockMvc.perform(generateMultipartPutRequest("/members/profile")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("profileImage").value(url))
                .andDo(document("profile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("POST -  /members/profile - S3 File Not Found")
    public void profileS3FileNotFoundFailed() throws Exception {
        //given
        //given
        MockMultipartFile file = new MockMultipartFile("profileImage", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());
        //when
        validInterceptorAndArgumentResolverMocking();
        doThrow(new S3FileNotFoundException()).when(memberService).uploadProfile(Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(generateMultipartPutRequest("/members/profile")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(S3FileNotFoundException.ERROR_CODE))
                .andDo(document("profileS3FileNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("POST - /members/profile - member not found failed")
    public void profileMemberNotFoundFailed() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("profileImage", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());
        String url = "https://de56jrhz7aye2.cloudfront.net/file";

        //when
        validInterceptorAndArgumentResolverMocking();
        doThrow(new MemberNotFoundException()).when(memberService).uploadProfile(Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(generateMultipartPutRequest("/members/profile")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("profileMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("POST - /members/profile - authorization header not found")
    public void profileHeaderNotFoundFailed() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("multipartFile", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(generateMultipartPutRequest("/members/profile")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("profileHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("POST - /members/profile - invalid token")
    public void profileInvalidTokenFailed() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("multipartFile", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(generateMultipartPutRequest("/members/profile")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("profileInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("DELETE -  /members/profile - success")
    public void deleteProfile() throws Exception {
        //when
        validInterceptorAndArgumentResolverMocking();
        doNothing().when(memberService).deleteProfile(Mockito.any());

        //then
        mockMvc.perform(delete("/members/profile"))
                .andExpect(status().isOk())
                .andDo(document("deleteProfile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("DELETE -  /members/profile - S3 File Not Found")
    public void deleteProfileS3FileNotFoundFailed() throws Exception {
        //when
        validInterceptorAndArgumentResolverMocking();
        doThrow(new S3FileNotFoundException()).when(memberService).deleteProfile(Mockito.any());

        //then
        mockMvc.perform(delete("/members/profile"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(S3FileNotFoundException.ERROR_CODE))
                .andDo(document("deleteProfileS3FileNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }


    @Test
    @DisplayName("DELETE -  /members/profile - authorization header not found")
    public void deleteProfileHeaderNotFoundFailed() throws Exception {
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        //then
        mockMvc.perform(delete("/members/profile"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("deleteProfileHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }


    @Test
    @DisplayName("DELETE - /members/profile - invalid token")
    public void deleteProfileInvalidTokenFailed() throws Exception {
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(delete("/members/profile"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("deleteProfileInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("DELETE - /members/profile - member not found")
    public void deleteProfileMemberNotFoundFailed() throws Exception {
        //given
        //when
        validInterceptorAndArgumentResolverMocking();
        doThrow(new MemberNotFoundException()).when(memberService).deleteProfile(Mockito.any());
        //then
        mockMvc.perform(delete("/members/profile"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(MemberNotFoundException.ERROR_CODE))
                .andDo(document("deleteProfileMemberNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("PUT - /members/me/bio - success")
    void updateBio() throws Exception {
        //given
        MemberBioUpdateRequest request = new MemberBioUpdateRequest("안녕하세요! 로키입니다.");

        //when
        validInterceptorAndArgumentResolverMocking();

        //then
        mockMvc.perform(put("/members/me/bio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("updateBio",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "       "})
    @DisplayName("PUT - /members/me/bio - invalid bio")
    void updateBioInvalidBioValueRequestFailed(String invalidBioValue) throws Exception {
        //given
        MemberBioUpdateRequest request = new MemberBioUpdateRequest(invalidBioValue);

        //when
        validInterceptorAndArgumentResolverMocking();

        //then
        mockMvc.perform(put("/members/me/bio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(BioValidationRequestException.ERROR_CODE))
                .andDo(document("updateBioInvalidBioValueRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "로키", "로키1", "로1키", "1로키"
    })
    @DisplayName("PUT - /me/nick-name - success")
    void updateNickName(String validNickName) throws Exception {
        //given
        NicknameRequest request = new NicknameRequest(validNickName);

        //when
        validInterceptorAndArgumentResolverMocking();

        //then
        mockMvc.perform(put("/members/me/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("updateNickName",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "로", "_로키", "로키_", "_로키_", "-로키", "로키-", " 로키", "로키 ", " 로키 ", "   "
    })
    @DisplayName("PUT - /me/nick-name - invalid nickName")
    void updateNickNameInvalidNickNameValueRequestFailed(String invalidNickName) throws Exception {
        //given
        NicknameRequest request = new NicknameRequest(invalidNickName);

        //when
        validInterceptorAndArgumentResolverMocking();

        //then
        mockMvc.perform(put("/members/me/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(NicknameValidationRequestException.ERROR_CODE))
                .andDo(document("updateNickNameInvalidNickNameValueRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("Get - /members/me/account - success")
    void getAccountInfo() throws Exception {
        //given
        AccountInfoResponse accountInfoResponse = AccountInfoResponse.of(new Account("","","",""));

        //when
        when(memberService.accountInfo(Mockito.any())).thenReturn(accountInfoResponse);
        validInterceptorAndArgumentResolverMocking();

        //then
        mockMvc.perform(get("/members/me/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("UNREGISTERED"))
                .andDo(document("accountInfo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("Get - /members/me/account - fail - authorization header not found")
    void getAccountInfoHeaderNotFoundFailed() throws Exception {
        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(get("/members/me/account"))
                .andExpect(status().isBadRequest())
                .andDo(document("accountInfoHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("Get - /members/me/account - success")
    void getAccountInfoInvalidTokenFailed() throws Exception {
        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(get("/members/me/account"))
                .andExpect(status().isBadRequest())
                .andDo(document("accountInfoInvalidTokenFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("Post - /members/me/account - success")
    void registerAccount() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("accountRegisterRequest", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());

        //when
        validInterceptorAndArgumentResolverMocking();

        //then
        mockMvc.perform(multipart("/members/me/account")
                .file(file)
                .param("name", "test")
                .param("account", "1234-5678-1234")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andDo(document("registerAccount",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("Post - /members/me/account - fail - account registered")
    void registerAccountFailWhenAccountRegistered() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("accountRegisterRequest", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());

        validInterceptorAndArgumentResolverMocking();
        doThrow(new AccountRegisteredException()).when(memberService).registerAccount(Mockito.any(), Mockito.any());

        //when
        mockMvc.perform(multipart("/members/me/account")
                .file(file)
                .param("name", "test")
                .param("account", "1234-5678-1234")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AccountRegisteredException.ERROR_CODE))
                .andDo(document("registerAccountFailRegistered",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("Post - /members/me/account - fail - account requesting")
    void registerAccountFailWhenAccountRequesting() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("accountRegisterRequest", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());

        validInterceptorAndArgumentResolverMocking();
        doThrow(new AccountRequestingException()).when(memberService).registerAccount(Mockito.any(), Mockito.any());

        //when
        mockMvc.perform(multipart("/members/me/account")
                .file(file)
                .param("name", "test")
                .param("account", "1234-5678-1234")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AccountRequestingException.ERROR_CODE))
                .andDo(document("registerAccountFailRequesting",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("Post - /members/me/account - fail - authorization header not found")
    public void registerAccountHeaderNotFoundFailed() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("accountRegisterRequest", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());

        //when
        doThrow(new AuthorizationHeaderNotFoundException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(multipart("/members/me/account")
                .file(file)
                .param("name", "test")
                .param("account", "1234-5678-1234")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AuthorizationHeaderNotFoundException.ERROR_CODE))
                .andDo(document("registerAccountHeaderNotFoundFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }


    @Test
    @DisplayName("Post - /members/me/account - fail invalid token")
    public void registerAccountInvalidTokenFailed() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("accountRegisterRequest", "testImage1.jpg",
                ContentType.IMAGE_JPEG.getMimeType(), "testImageBinary".getBytes());

        //when
        doThrow(new InvalidTokenException()).when(authenticationInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());

        //then
        mockMvc.perform(multipart("/members/me/account")
                .file(file)
                .param("name", "test")
                .param("account", "1234-5678-1234")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidTokenException.ERROR_CODE))
                .andDo(document("registerAccountInvalidTokenFailed",
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

    private MockMultipartHttpServletRequestBuilder generateMultipartPutRequest(String url) {
        MockMultipartHttpServletRequestBuilder putRequest = multipart(url);

        putRequest.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        return putRequest;

    }
}
