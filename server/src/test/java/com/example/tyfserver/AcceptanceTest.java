package com.example.tyfserver;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//@Transactional
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;
    }

    protected static RequestSpecification apiTemplate() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    protected static RequestSpecification apiTemplate(Object body) {
        return apiTemplate()
                .body(body);
    }

    protected static ValidatableResponse post(String url, Object body) {
        return apiTemplate(body)
                .post(url)
                .then().log().all();
    }

    protected static ValidatableResponse get(String url) {
        return apiTemplate()
                .get(url)
                .then().log().all();
    }

    protected static RequestSpecification authTemplate(String bearerToken) {
        return apiTemplate()
                .headers("Authorization",
                        "Bearer " + bearerToken);
    }


}
