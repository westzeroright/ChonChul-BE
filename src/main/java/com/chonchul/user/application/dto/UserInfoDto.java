package com.chonchul.user.application.dto;

public record UserInfoDto(
        String name,
        int number,
        String department,
        String phoneNumber,
        String email
) {
}
