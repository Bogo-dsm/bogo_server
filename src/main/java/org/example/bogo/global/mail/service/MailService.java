package org.example.bogo.global.mail.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.bogo.global.error.exception.BogoException;
import org.example.bogo.global.error.exception.GlobalErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateCode() {
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            // CHARACTERS 문자열의 길이 내에서 무작위 인덱스를 선택
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    public void sendVerificationEmail(String toEmail, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("이메일 인증");

            String body = "";
            body += "<h3>요청하신 인증 번호입니다.</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>감사합니다.</h3>";
            message.setText(body, "UTF-8", "html");

            javaMailSender.send(message);
        } catch (MessagingException | MailException e) {
            throw new BogoException(GlobalErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
//@Service
//@RequiredArgsConstructor
//public class MailService {
//
//    private static final SecureRandom RANDOM = new SecureRandom();
//    private final JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String senderEmail;
//
//    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//
//    public String generateCode() {
//        StringBuilder code = new StringBuilder(6);
//        for (int i = 0; i < 6; i++) {
//            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
//        }
//        return code.toString();
//    }
//
//    // 1. MimeMessage를 생성하는 로직 분리
//    private MimeMessage createVerificationMessage(String toEmail, String code) throws MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        message.setFrom(senderEmail);
//        message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
//        message.setSubject("이메일 인증");
//
//        String body = "<h3>요청하신 인증 번호입니다.</h3>" +
//                "<h1>" + code + "</h1>" +
//                "<h3>감사합니다.</h3>";
//
//        message.setText(body, "UTF-8", "html");
//        return message;
//    }
//
//    // 2. 외부에서 호출하는 발송 메서드 (성공 여부 반환)
//    public String sendSimpleMessage(String sendEmail) {
//        String authCode = generateCode();
//
//        try {
//            MimeMessage message = createVerificationMessage(sendEmail, authCode);
//            javaMailSender.send(message);
//            return authCode; // 검증을 위해 생성된 코드를 반환하는 것이 일반적입니다.
//        } catch (MessagingException | MailException e) {
//            // log.error("Mail sending failed", e); // 로깅 권장
//            return null;
//        }
//    }
//}
