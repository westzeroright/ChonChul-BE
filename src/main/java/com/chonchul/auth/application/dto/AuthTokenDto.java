package com.chonchul.auth.application.dto;

public record AuthTokenDto(String accessToken, String refreshToken) {
}
