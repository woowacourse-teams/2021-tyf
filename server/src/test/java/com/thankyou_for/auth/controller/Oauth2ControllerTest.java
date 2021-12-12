package com.thankyou_for.auth.controller;

import com.thankyou_for.auth.config.AuthenticationArgumentResolver;
import com.thankyou_for.auth.config.AuthenticationInterceptor;
import com.thankyou_for.auth.dto.Oauth2Request;
import com.thankyou_for.auth.dto.SignUpResponse;
import com.thankyou_for.auth.dto.TokenResponse;
import com.thankyou_for.auth.service.AuthenticationService;
import com.thankyou_for.auth.service.Oauth2Service;
import com.thankyou_for.member.dto.SignUpReadyResponse;
import com.thankyou_for.member.dto.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thankyou_for.auth.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = Oauth2Controller.class)
@AutoConfigureRestDocs
class Oauth2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Oauth2Service oauth2Service;

    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;
    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;
    @MockBean
    private AuthenticationService authenticationService;

    private static MockedStatic<Oauth2Request> oauth2Request;

    @BeforeEach
    void setUp() {
        oauth2Request = mockStatic(Oauth2Request.class);
    }

    @AfterEach
    void tearDown() {
        oauth2Request.close();
    }

    @Test
    @DisplayName("/oauth2/login/{oauth} - success")
    public void login() throws Exception {
        //given
        when(Oauth2Request.generateLoginInfoFrom(Mockito.anyString())).thenReturn(null);
        when(oauth2Service.login(Mockito.any(), Mockito.anyString()))
                .thenReturn(new TokenResponse("token"));
        //when&then
        mockMvc.perform(get("/oauth2/login/KAKAO")
                .param("code", "anycode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andDo(print())
                .andDo(document("login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/oauth2/login/{oauth} - UnregisteredMember Failed")
    public void loginUnregisteredMemberFailed() throws Exception {
        //given
        when(Oauth2Request.generateLoginInfoFrom(Mockito.anyString())).thenReturn(null);
        doThrow(new UnregisteredMemberException())
                .when(oauth2Service).login(Mockito.any(), Mockito.anyString());
        //when&then
        mockMvc.perform(get("/oauth2/login/KAKAO")
                .param("code", "anycode"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(UnregisteredMemberException.ERROR_CODE))
                .andDo(print())
                .andDo(document("loginUnregisteredMemberFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/oauth2/signup/ready/{oauth} - success")
    public void readySignUp() throws Exception {
        //given
        when(Oauth2Request.generateSignUpInfoFrom(Mockito.anyString())).thenReturn(null);
        when(oauth2Service.readySignUp(Mockito.any(), Mockito.anyString()))
                .thenReturn(new SignUpReadyResponse("thankyou@gmail.com", "KAKAO"));
        //when&then
        mockMvc.perform(get("/oauth2/signup/ready/KAKAO")
                .param("code", "anycode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("thankyou@gmail.com"))
                .andExpect(jsonPath("$.oauthType").value("KAKAO"))
                .andDo(print())
                .andDo(document("readySignUp",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/oauth2/signup/ready/{oauth} - AlreadyRegisteredInSameOauth2Type Failed")
    public void readySignUpAlreadyRegisteredInSameOauth2TypeFailed() throws Exception {
        //given
        when(Oauth2Request.generateSignUpInfoFrom(Mockito.anyString())).thenReturn(null);
        doThrow(new AlreadyRegisteredInSameOauth2TypeException("token"))
                .when(oauth2Service).readySignUp(Mockito.any(), Mockito.anyString());
        //when&then
        mockMvc.perform(get("/oauth2/signup/ready/KAKAO")
                .param("code", "anycode"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AlreadyRegisteredInSameOauth2TypeException.ERROR_CODE))
                .andDo(print())
                .andDo(document("readySignUpAlreadyRegisteredInSameOauth2TypeFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/oauth2/signup/ready/{oauth} - AlreadyRegisteredException Failed")
    public void readySignUpAlreadyRegisteredFailed() throws Exception {
        //given
        when(Oauth2Request.generateSignUpInfoFrom(Mockito.anyString())).thenReturn(null);
        doThrow(new AlreadyRegisteredException("oauth2Type"))
                .when(oauth2Service).readySignUp(Mockito.any(), Mockito.anyString());
        //when&then
        mockMvc.perform(get("/oauth2/signup/ready/KAKAO")
                .param("code", "anycode"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(AlreadyRegisteredException.ERROR_CODE))
                .andDo(print())
                .andDo(document("readySignUpAlreadyRegisteredFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/oauth2/signup/ready/{oauth} - InvalidOauth2TypeException Failed")
    public void readySignUpInvalidOauth2TypeFailed() throws Exception {
        //given
        when(Oauth2Request.generateSignUpInfoFrom(Mockito.anyString())).thenReturn(null);
        doThrow(new InvalidOauth2TypeException())
                .when(oauth2Service).readySignUp(Mockito.any(), Mockito.anyString());
        //when&then
        mockMvc.perform(get("/oauth2/signup/ready/KAKAO")
                .param("code", "anycode"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(InvalidOauth2TypeException.ERROR_CODE))
                .andDo(print())
                .andDo(document("readySignUpInvalidOauth2TypeFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/oauth2/signup - success")
    public void signUp() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("email@gmail.com", "KAKAO", "nickname", "pagename");
        when(oauth2Service.signUp(Mockito.any(SignUpRequest.class)))
                .thenReturn(new SignUpResponse("token", "pagename"));
        //when&then
        mockMvc.perform(post("/oauth2/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andExpect(jsonPath("$.pageName").value("pagename"))
                .andDo(print())
                .andDo(document("signUp",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/oauth2/signup - signUpRequestException Failed")
    public void signUpRequestFailed() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("invalid", "KAKAO", "nickname", "pagename");

        //when&then
        mockMvc.perform(post("/oauth2/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(SignUpRequestException.ERROR_CODE))
                .andDo(print())
                .andDo(document("signUpRequestFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    @DisplayName("/oauth2/signup - InvalidOauth2TypeException Failed")
    public void signUpInvalidOauth2TypeFailed() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("email@email.com", "invalidOauth", "nickname", "pagename");
        doThrow(new InvalidOauth2TypeException())
                .when(oauth2Service).signUp(Mockito.any(SignUpRequest.class));

        //when&then
        mockMvc.perform(post("/oauth2/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(InvalidOauth2TypeException.ERROR_CODE))
                .andDo(print())
                .andDo(document("signUpInvalidOauth2TypeFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
        ;
    }
}