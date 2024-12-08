package com.chonchul.auth.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDto(
        @NotBlank(message = "현재 비밀번호를 입력해주세요.")
        String currentPassword,
        @NotBlank(message = "새 비밀번호를 입력해주세요.")
        String newPassword,
        @NotBlank(message = "새 비밀번호 확인을 입력해주세요.")
        String confirmPassword) {
}
