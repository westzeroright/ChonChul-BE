package com.chonchul.auth.presentation;

import com.chonchul.auth.application.token.TokenResolver;
import com.chonchul.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenResolver tokenResolver;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hashLoginUserAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        return hashLoginUserAnnotation;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {

        String token = webRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Long userId = tokenResolver.getUserDataByAccessToken(token);
        return userRepository.findById(userId).get();
    }
}
