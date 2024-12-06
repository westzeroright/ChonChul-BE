package com.chonchul.auth.application.service;

import com.chonchul.auth.application.dto.AuthTokenDto;
import com.chonchul.auth.application.token.TokenProvider;
import com.chonchul.auth.application.token.TokenResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final TokenProvider tokenProvider;
    private final TokenResolver tokenResolver;

    public AuthTokenDto createAuthToken(Long userId) {
        String accessToken = tokenProvider.createAccessToken(userId);
        String refreshToken = tokenProvider.createRefreshToken(userId);
        return new AuthTokenDto(accessToken, refreshToken);
    }

    public AuthTokenDto reissue(String refreshToken) {
        Long userid = tokenResolver.getUserDataByRefreshToken(refreshToken);
        return createAuthToken(userid);
    }
}
