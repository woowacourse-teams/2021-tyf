package com.example.tyfserver.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class ApiSender {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static String send(String url, HttpMethod method, HttpEntity entity) {

        return restTemplate.exchange(
                url,
                method,
                entity,
                String.class
        ).getBody();
    }
}
