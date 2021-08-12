package com.example.tyfserver.common.util;

import com.example.tyfserver.common.exception.SendingMailFailedException;
import com.example.tyfserver.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SmtpMailConnector {

    private static final String PREFIX_SUBJECT = "[Thank You For]";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    public void sendVerificationCode(String mailAddress, String verificationCode) {
        Context context = new Context();
        context.setVariable("code", verificationCode);
        String message = templateEngine.process("mail-verification-code.html", context);
        sendMail("환불 인증번호", message, mailAddress);
    }

    public void sendExchangeApprove(String mailAddress) {
        Context context = new Context();
        context.setVariable("head", "정산 승인 완료");
        context.setVariable("body", "정산이 완료되었습니다.");
        String message = templateEngine.process("mail-basic.html", context);
        sendMail("정산 승인 완료", message, mailAddress);
    }

    public void sendExchangeReject(String mailAddress, String rejectReason) {
        Context context = new Context();
        context.setVariable("head", "정산 승인 반려");
        context.setVariable("body", "정산이 반려되었습니다. \n " +
                "거절사유 : " + rejectReason);
        String message = templateEngine.process("mail-basic.html", context);
        sendMail("정산 승인 반려", message, mailAddress);
    }

    public void sendAccountApprove(String mailAddress) {
        Context context = new Context();
        context.setVariable("head", "정산 계좌 승인 완료");
        context.setVariable("body", "정산계좌 신청이 승인되었습니다.");
        String message = templateEngine.process("mail-basic.html", context);
        sendMail("정산 계좌 승인 완료", message, mailAddress);
    }

    public void sendAccountReject(String mailAddress, String rejectReason) {
        Context context = new Context();
        context.setVariable("head", "정산 계좌 승인 반려");
        context.setVariable("body", "정산계좌 신청이 반려되었습니다. \n " +
                "반려사유 : " + rejectReason);
        String message = templateEngine.process("mail-basic.html", context);
        sendMail("정산 계좌 승인 반려", message, mailAddress);
    }

    public void sendDonationComplete(Payment payment) {
        Context context = new Context();
        context.setVariable("merchant_id", payment.getMerchantUid());
        context.setVariable("creator_name", payment.getPageName());
        context.setVariable("donation_amount", payment.getAmount());
        context.setVariable("date", payment.getCreatedAt());

        String message = templateEngine.process("mail-donation-complete.html", context);
        sendMail("후원 성공", message, payment.getEmail());

    }

    private void sendMail(String subject, String htmlText, String toEmail) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mail, true, "UTF-8");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(PREFIX_SUBJECT + subject);
            mimeMessageHelper.setText(htmlText, true);
            mimeMessageHelper.addInline("tyf-logo", getLogo());
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            throw new SendingMailFailedException();
        }
    }

    private FileSystemResource getLogo() {
        File file = null;
        try {
            file = new ClassPathResource("static/logo.png").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FileSystemResource(file);
    }
}
