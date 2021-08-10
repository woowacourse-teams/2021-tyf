package com.example.tyfserver.admin.config;

import com.example.tyfserver.admin.dto.AdminMember;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.auth.util.AuthorizationExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class AdminArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationService authenticationService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AdminMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = AuthorizationExtractor.extract(request);
        return authenticationService.createAdminMemberByToken(token);
    }
}
