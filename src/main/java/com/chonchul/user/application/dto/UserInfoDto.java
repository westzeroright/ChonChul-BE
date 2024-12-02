package com.chonchul.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@jnu\\.ac\\.kr$",
                message = "유효하지 않은 이메일 형식입니다. 학교 메일을 사용해주세요."
        )
        String email
) {
}
