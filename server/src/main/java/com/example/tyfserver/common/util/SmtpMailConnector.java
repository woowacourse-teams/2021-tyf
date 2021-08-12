package com.example.tyfserver.common.util;

import com.amazonaws.util.IOUtils;
import com.example.tyfserver.common.exception.SendingMailFailedException;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SmtpMailConnector {

    private static final String PREFIX_SUBJECT = "[Thank You For]";
    private static final String CREATOR_PREFIX_DOMAIN = "https://thankyou-for.com/creator/";

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

    public void sendDonationComplete(Payment payment, Member member) {
        Context context = new Context();
        context.setVariable("page_url", CREATOR_PREFIX_DOMAIN + payment.getPageName());
        context.setVariable("merchant_id", payment.getMerchantUid());
        context.setVariable("creator_name", member.getNickname());
        context.setVariable("donation_amount", payment.getAmount());
        context.setVariable("date",    payment.getCreatedAt().now().
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));



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
            mimeMessageHelper.addInline("tyf-logo", getLogo(),"application/octect-stream");
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            throw new SendingMailFailedException();
        }
    }

    private ByteArrayResource getLogo() {
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource("static/logo.png").getInputStream();
            return new ByteArrayResource(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new SendingMailFailedException();
    }
}
