package com.example.tyfserver;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.service.Oauth2Service;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import com.example.tyfserver.auth.util.Oauth2ServiceConnector;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.SignUpReadyResponse;
import com.example.tyfserver.member.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private Oauth2ServiceConnector oauth2ServiceConnector;

    private Member mainMember = new Member("main@gmail.com", "nickname", "pageName", Oauth2Type.GOOGLE, "profileImage");

    protected static RequestSpecification apiTemplate() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    protected static ValidatableResponse post(String url, Object body) {
        return apiTemplate()
                .body(body)
                .post(url)
                .then().log().all();
    }

    protected static ValidatableResponse get(String url) {
        return apiTemplate()
                .get(url)
                .then().log().all();
    }

    protected static RequestSpecification paramTemplate(String name, String param) {
        return RestAssured
                .given().log().all()
                .param(name, param)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    protected static ValidatableResponse paramGet(String url, String name, String param) {
        return paramTemplate(name, param)
                .get(url)
                .then().log().all();
    }

    private static RequestSpecification authTemplate(String bearerToken) {
        return apiTemplate()
                .auth().oauth2(bearerToken);
    }

    protected static ValidatableResponse authPost(String url, String token, Object body) {
        return authTemplate(token)
                .body(body)
                .post(url)
                .then().log().all();
    }

    protected static ValidatableResponse authGet(String url, String token) {
        return authTemplate(token)
                .get(url)
                .then().log().all();
    }

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;
        memberRepository.save(mainMember);
        when(oauth2ServiceConnector.getEmailFromOauth2(any(), any()))
                .thenReturn(mainMember.getEmail());
    }

    @AfterEach
    void tearDown() {
        memberRepository.delete(mainMember);
    }
}
