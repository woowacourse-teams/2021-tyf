package com.example.tyfserver.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class ApiSender {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    static { // todo: 한글대신 유니코드 나오는 현상
        REST_TEMPLATE.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    private ApiSender() {
    }

    public static String send(String url, HttpMethod method, HttpEntity entity) {
        return REST_TEMPLATE.exchange(url, method, entity, String.class).getBody();
    }

    public static <T> T send(String url, HttpMethod method, HttpEntity entity, Class<T> returnType) {
        return REST_TEMPLATE.exchange(url, method, entity, returnType).getBody();
    }
}
