package com.example.tyfserver.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpMailConnector {

    private final JavaMailSender javaMailSender;

    public void sendVerificationCode(String mailAddress, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject("[Thank You For] 환불 인증번호");
        message.setText("인증번호:" + verificationCode);

        javaMailSender.send(message);
    }

    public void sendExchangeResult(String mailAddress, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject("[Thank You For] 정산 계정 요청 결과");
        message.setText(text);

        javaMailSender.send(message);
    }
}
