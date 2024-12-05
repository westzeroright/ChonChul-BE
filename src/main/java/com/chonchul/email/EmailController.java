package com.chonchul.email;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String senEmail(@RequestParam String email) {
        emailService.isValidEmail(email);
        int code = emailService.sendMail(email);
        String numberCode = "" + code;
        return numberCode;
    }

    @Operation(summary = "인증코드 검사", description = "인증코드가 유효한지 검사합니다.")
    @PostMapping("/verify")
    public String verifyCode(@RequestBody EmailReqDto emailReqDto) {
        Boolean checked = emailService.checkAuthCode(emailReqDto.email(), emailReqDto.code());
        if(checked){
            return "ok";
        }
        else{
            throw new NullPointerException("만료기간 지났을수도");
        }
    }
}
