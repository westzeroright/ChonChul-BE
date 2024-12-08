package com.chonchul.auth.application.service;

import com.chonchul.auth.application.RedisUtil;
import com.chonchul.auth.application.exception.InvalidMailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${email.sender}")
    private String senderEmail;
    private static int code;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    public static final Long emailCodeExpireTime = 60 * 1L;
    private static String temporaryPassword;

    public static void createCode() {
        code = (int) (Math.random() * (90000)) + 100000;
    }

    public static void generateTemporaryPassword() {
        String charactoer = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i<6;i++) {
            int index = random.nextInt(charactoer.length());
            sb.append(charactoer.charAt(index));
        }
        temporaryPassword = sb.toString();
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

        log.info("인증번호: " + code);
        return message;
    }

    public MimeMessage createFormPassword(String email) {
        generateTemporaryPassword();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("임시 비밀번호 발급");
            String body = "";
            body += "<h3>" + "임시 비밀번호입니다." + "</h3>";
            body += "<h1>" + temporaryPassword + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        log.info("임시 비밀번호: " + code);
        return message;
    }

    public int sendMail(String email) {
        MimeMessage message = createForm(email);
        javaMailSender.send(message);
        redisUtil.setDataExpire(Integer.toString(code), email, emailCodeExpireTime);
        redisUtil.setData(email, EmailStatus.PENDING.name());
        return code;
    }

    public String sendPassword(String email) {
        MimeMessage message = createFormPassword(email);
        javaMailSender.send(message);
        return temporaryPassword;
    }

    public boolean checkAuthCode(String email, String code) {
        if (redisUtil.getData(code) == null) {
            return false;
        } else if (redisUtil.getData(code).equals(email)) {
            redisUtil.setData(email, EmailStatus.VERIFIED.name());
            return true;
        } else {
            return false;
        }
    }

    public boolean isVerified(String email) {
        Object status = redisUtil.getData(email);
        return EmailStatus.VERIFIED.name().equals(status);
    }

    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@jnu\\.ac\\.kr$";
        boolean isValid = email.matches(regex);
        if (!isValid) {
            throw new InvalidMailException();
        }
        return true;
    }

    public void checkEmail(String email) {
        if (!(isValidEmail(email) && isVerified(email))) {

        }
    }
}
