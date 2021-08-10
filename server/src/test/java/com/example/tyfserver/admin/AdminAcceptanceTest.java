package com.example.tyfserver.admin;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 정산_신청_목록_조회() {
        return get("/admin/list/exchange").extract();
    }

    @Test
    @DisplayName("정산신청 목록 조회")
    public void exchangeList() {
        List<ExchangeResponse> list = 정산_신청_목록_조회().body().jsonPath().getList(".", ExchangeResponse.class);

        assertThat(list).hasSize(0);
    }
}
