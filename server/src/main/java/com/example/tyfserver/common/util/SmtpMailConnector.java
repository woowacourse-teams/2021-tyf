package com.example.tyfserver.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpMailConnector {
    private static final String PREFIX_SUBJECT = "[Thank You For]";
    private final JavaMailSender javaMailSender;

    public void sendVerificationCode(String mailAddress, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject(PREFIX_SUBJECT + "환불 인증번호");
        message.setText("인증번호:" + verificationCode);

        javaMailSender.send(message);
    }

    public void sendAccountApprove(String mailAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject(PREFIX_SUBJECT + "정산 계좌 승인 완료");
        message.setText("정산계좌 신청이 승인되었습니다.");

        javaMailSender.send(message);
    }

    public void sendAccountCancel(String mailAddress, String cancelReason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject(PREFIX_SUBJECT + "정산 계좌 승인 반려");
        message.setText("정산계좌 신청이 반려되었습니다. \n " +
                "반려사유 : " + cancelReason);

        javaMailSender.send(message);
    }
}
