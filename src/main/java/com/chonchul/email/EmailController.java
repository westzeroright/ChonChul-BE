package com.chonchul.email;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emails")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String senEmail(@RequestParam String email) {
        emailService.isValidEmail(email);
        int code = emailService.sendMail(email);
        String numberCode = "" + code;
        return numberCode;
    }

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
