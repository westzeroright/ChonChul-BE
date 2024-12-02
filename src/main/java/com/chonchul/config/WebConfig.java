package com.chonchul.config;

import com.chonchul.auth.LoginUserArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginMemberArgumentResolver;

    public WebConfig(LoginUserArgumentResolver loginMemberArgumentResolver) {
        this.loginMemberArgumentResolver = loginMemberArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginMemberArgumentResolver);
    }
}
