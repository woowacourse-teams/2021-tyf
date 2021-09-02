package com.example.tyfserver.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class ApiSender {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private ApiSender() {
    }

    public static <T> String send(String url, HttpMethod method, HttpEntity<T> entity) {
        return send(url, method, entity, String.class);
    }

    public static <T, U> T send(String url, HttpMethod method, HttpEntity<U> entity, Class<T> returnType) {
        return REST_TEMPLATE.exchange(url, method, entity, returnType).getBody();
    }
}
