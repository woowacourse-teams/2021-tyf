package com.example.tyfserver.common.util;

import com.example.tyfserver.common.exception.SendingMailFailedException;
import com.example.tyfserver.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class SmtpMailConnector {

    private static final String PREFIX_SUBJECT = "[Thank You For]";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendVerificationCode(String mailAddress, String verificationCode) {
        Context context = new Context();
        context.setVariable("code", verificationCode);
        String message = templateEngine.process( "verification-code.html", context);

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mail, false, "UTF-8");
            mimeMessageHelper.setTo(mailAddress);
            mimeMessageHelper.setSubject(PREFIX_SUBJECT + "환불 인증번호");
            mimeMessageHelper.setText(message, true);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            throw new SendingMailFailedException();
        }
    }

    public void sendExchangeApprove(String mailAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject(PREFIX_SUBJECT + "정산 승인 완료");
        message.setText("정산이 완료되었습니다.");

        javaMailSender.send(message);
    }

    public void sendExchangeReject(String mailAddress, String rejectReason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject(PREFIX_SUBJECT + "정산 승인 실패");
        message.setText("정산이 거절되었습니다. \n " +
                "거절사유 : " + rejectReason);
        javaMailSender.send(message);
    }

    public void sendAccountApprove(String mailAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject(PREFIX_SUBJECT + "정산 계좌 승인 완료");
        message.setText("정산계좌 신청이 승인되었습니다.");

        javaMailSender.send(message);
    }

    public void sendAccountReject(String mailAddress, String rejectReason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddress);
        message.setSubject(PREFIX_SUBJECT + "정산 계좌 승인 반려");
        message.setText("정산계좌 신청이 반려되었습니다. \n " +
                "반려사유 : " + rejectReason);
        javaMailSender.send(message);
    }

    public void sendDonationComplete(Payment payment) {
        Context context = new Context();
        context.setVariable("merchant_id", payment.getMerchantUid());
        context.setVariable("creator_name", payment.getPageName());
        context.setVariable("donation_amount", payment.getAmount());
        context.setVariable("date", payment.getCreatedAt());

        String message = templateEngine.process("donation-complete.html", context);

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mail, false, "UTF-8");
            mimeMessageHelper.setTo(payment.getEmail());
            mimeMessageHelper.setSubject(PREFIX_SUBJECT + "후원 성공");
            mimeMessageHelper.setText(message, true);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            throw new SendingMailFailedException();
        }
    }
}
