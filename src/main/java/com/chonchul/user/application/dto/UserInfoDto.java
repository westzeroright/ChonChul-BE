package com.chonchul.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserInfoDto(
        @NotBlank
        String name,
        @NotNull
        int number,
        @NotBlank
        String department,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String email
) {
}
