package com.chonchul.auth.application.service;

import com.chonchul.auth.application.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String senderEmail = "westzeroright@gmail.com";
    private static int code;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;


    public static void createCode() {
        code = (int) (Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    public MimeMessage createForm(String email) {
        createCode();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String email) {
        MimeMessage message = createForm(email);
        javaMailSender.send(message);
        redisUtil.setDataExpire(Integer.toString(code), email, 60 * 1L);
        redisUtil.setData(email, EmailStatus.PENDING);
        return code;
    }

    public boolean checkAuthCode(String email, String code) {
        if (redisUtil.getData(code) == null) {
            return false;
        } else if (redisUtil.getData(code).equals(email)) {
            redisUtil.setData(email, EmailStatus.VERIFIED);
            return true;
        } else {
            return false;
        }
    }

    public boolean isVerified(String email) {
        Object status = redisUtil.getData(email);
        return EmailStatus.VERIFIED.equals(status);
    }

    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@jnu\\.ac\\.kr$";
        boolean isValid = email.matches(regex);
        if (!isValid) {
            throw new RuntimeException();
        }
        return true;
    }
}
