package com.chonchul.auth.presentation;

import com.chonchul.auth.application.token.TokenResolver;
import com.chonchul.user.application.exception.NotFoundUserException;
import com.chonchul.user.persistence.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final TokenResolver tokenResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        logRequestDetails(request);

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorization.replaceAll("Bearer ", "");

        if (token != null && token.length() > 10) {

            if (vaildToken(token)) {
                ObjectMapper objectMapper = new ObjectMapper();

                String member = objectMapper.writeValueAsString(tokenResolver.getUserDataByAccessToken(token));
                User accessMember = objectMapper.readValue(member, User.class);

                request.setAttribute("member", accessMember);
                Long memberId = tokenResolver.getUserDataByAccessToken(token);
                return true;
            }
        } else {
            throw new NotFoundUserException();
        }
        return false;
    }

    private static void logRequestDetails(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
    }

    private boolean vaildToken(String token) {
        try {
            tokenResolver.getUserDataByAccessToken(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new RuntimeException();
        }
    }
}
