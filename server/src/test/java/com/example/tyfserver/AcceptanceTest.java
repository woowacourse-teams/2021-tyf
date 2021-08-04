package com.example.tyfserver;

import com.example.tyfserver.auth.util.Oauth2ServiceConnector;
import com.example.tyfserver.common.util.S3Connector;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @MockBean
    private Oauth2ServiceConnector oauth2ServiceConnector;
    @MockBean
    private S3Connector s3Connector;

    public static final String DEFAULT_EMAIL = "thankyou@gmail.com";
    public static final String DEFAULT_PROFILE_IMAGE = "profileImage";


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

    protected static RequestSpecification paramTemplate(String key, String value) {
        return RestAssured
                .given().log().all()
                .param(key, value)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    protected static ValidatableResponse paramGet(String url, String key, String value) {
        return paramTemplate(key, value)
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

    protected static ValidatableResponse authPut(String url, String token, Object body) {
        return authTemplate(token)
                .body(body)
                .put(url)
                .then().log().all();
    }

    protected static ValidatableResponse authGet(String url, String token) {
        return authTemplate(token)
                .get(url)
                .then().log().all();
    }

    protected static ValidatableResponse authDelete(String url, String token) {
        return authTemplate(token)
                .delete(url)
                .then().log().all();
    }

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;
        when(oauth2ServiceConnector.getEmailFromOauth2(any(), any()))
                .thenReturn(DEFAULT_EMAIL);
        when(s3Connector.upload(any(), any()))
                .thenReturn(DEFAULT_PROFILE_IMAGE);
        doNothing().when(s3Connector).delete(anyString());
    }
}
