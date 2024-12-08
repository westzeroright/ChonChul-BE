package com.chonchul.auth.presentation.controller;

import com.chonchul.auth.application.dto.EmailReqDto;
import com.chonchul.auth.application.dto.EmailVerifyDto;
import com.chonchul.auth.application.exception.EmailCodeExpireException;
import com.chonchul.auth.application.service.EmailService;
import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emails")
@Tag(name = "이메일 API", description = "이메일 인증 관련 API")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "메일 전송", description = "메일로 인증코드를 전송합니다.")
    @PostMapping("/send")
    public ResponseEntity<SuccessBody<Void>> senEmail(@RequestBody EmailReqDto email) {
        emailService.isValidEmail(email.email());
        emailService.sendMail(email.email());
        return ResponseEntityGenerator.success(null, "메일 전송 성공", HttpStatus.OK);
    }

    @Operation(summary = "인증코드 검사", description = "인증코드가 유효한지 검사합니다.")
    @PostMapping("/verify")
    public ResponseEntity<SuccessBody<Void>> verifyCode(@RequestBody EmailVerifyDto emailReqDto) {
        Boolean checked = emailService.checkAuthCode(emailReqDto.email(), emailReqDto.code());
        if (checked) {
            return ResponseEntityGenerator.success(null, "메일 인증 성공", HttpStatus.OK);
        } else {
            throw new EmailCodeExpireException();
        }
    }
}
