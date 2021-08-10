package com.example.tyfserver.admin.config;

import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.auth.util.AuthorizationExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        authenticationService.validateToken(token);
        return true;
    }
}
