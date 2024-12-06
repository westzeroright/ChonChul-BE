package com.chonchul.auth.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginReqDto(
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@jnu\\.ac\\.kr$",
                message = "유효하지 않은 이메일 형식입니다. 학교 메일을 사용해주세요."
        )
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password) {
}
