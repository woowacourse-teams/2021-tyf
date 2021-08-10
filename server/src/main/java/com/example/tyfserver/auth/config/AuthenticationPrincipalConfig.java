package com.example.tyfserver.auth.config;

import com.example.tyfserver.admin.config.AdminArgumentResolver;
import com.example.tyfserver.admin.config.AdminInterceptor;
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
    private final AdminArgumentResolver adminArgumentResolver;
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/banners", "/banners/me", "/donations/me", "/members/me", "/members/me/point",
                        "/members/profile", "/members/me/bio", "/members/me/nickname", "/members/me/detailedPoint",
                        "/members/me/account");

        registry.addInterceptor(adminInterceptor)
                .excludePathPatterns("/admin/login")
                .addPathPatterns("/admin", "/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
        resolvers.add(refundAuthenticationArgumentResolver);
        resolvers.add(adminArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*")
                .allowedOrigins(
                        "http://localhost:9000",
                        "https://thankyou-for.com",
                        "https://thirsty-euler-f61b80.netlify.app"
                );
    }
}
