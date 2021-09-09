package com.example.tyfserver.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final AuthenticationArgumentResolver authenticationArgumentResolver;
    private final RefundAuthenticationArgumentResolver refundAuthenticationArgumentResolver;
    private final AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .excludePathPatterns("/admin/login")
                .addPathPatterns("/members/me", "/members/me/point", "/members/profile", "/members/me/bio",
                        "/members/me/nickname", "/members/me/detailedPoint", "/members/me/account", "/members/me/exchange")
                .addPathPatterns("/donations/me")
                .addPathPatterns("/banners", "/banners/me")
                .addPathPatterns("/admin", "/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
        resolvers.add(refundAuthenticationArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*")
                .allowedOrigins(
                        "http://localhost:9000",
                        "https://thankyou-for.com",
                        "https://admin.thankyou-for.com"
                );
    }
}
