package com.example.tyfserver.common.domain;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_TYPE = "Bearer";

    private AuthorizationExtractor() {
    }

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(AUTHORIZATION_TYPE.toLowerCase())) {
                return value.substring(AUTHORIZATION_TYPE.length()).trim();
            }
        }

        return null;
    }
}
