package com.chonchul.auth.application.service;

import com.chonchul.auth.application.dto.AuthTokenDto;
import com.chonchul.auth.application.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final TokenProvider jwtUtil;

    public AuthTokenDto createAuthToken(Long userId) {
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);
        return new AuthTokenDto(accessToken, refreshToken);
    }
}
