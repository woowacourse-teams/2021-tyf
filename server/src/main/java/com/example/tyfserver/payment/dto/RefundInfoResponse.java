package com.example.tyfserver.payment.dto;

import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.payment.domain.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundInfoResponse {

    private CreatorInfoResponse creator;
    private PaymentInfoResponse payment;

    public RefundInfoResponse(CreatorInfoResponse creator, PaymentInfoResponse payment) {
        this.creator = creator;
        this.payment = payment;
    }

    public RefundInfoResponse(Payment payment, Member member) {
        this(new CreatorInfoResponse(member), new PaymentInfoResponse(payment));
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class CreatorInfoResponse {

        private String nickname;
        private String pageName;

        public CreatorInfoResponse(String nickname, String pageName) {
            this.nickname = nickname;
            this.pageName = pageName;
        }

        public CreatorInfoResponse(Member member) {
            this(member.getNickname(), member.getPageName());
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PaymentInfoResponse {

        private Long amount;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy'/'MM'/'dd'/' HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        public PaymentInfoResponse(Long amount, LocalDateTime createdAt) {
            this.amount = amount;
            this.createdAt = createdAt;
        }

        public PaymentInfoResponse(Payment payment) {
            this(payment.getAmount(), payment.getCreatedAt());
        }
    }
}
