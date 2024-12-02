package com.chonchul.auth.token;

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
