package com.example.tyfserver.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class ApiSender {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private ApiSender() {
    }

    public static String send(String url, HttpMethod method, HttpEntity entity) {
        return REST_TEMPLATE.exchange(url, method, entity, String.class).getBody();
    }
}
