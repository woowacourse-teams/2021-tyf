package com.example.tyfserver.auth.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

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

        throw new RuntimeException("Authorization Header Not Found");
    }
}
