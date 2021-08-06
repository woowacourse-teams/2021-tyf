package com.example.tyfserver.payment.dto;

import com.example.tyfserver.donation.domain.Donation;
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
    private DonationInfoResponse donation;

    public RefundInfoResponse(Payment payment, Donation donation, Member member) {
        this.creator = new CreatorInfoResponse(member);
        this.donation = new DonationInfoResponse(donation, payment);
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class CreatorInfoResponse {

        private String nickName;
        private String pageName;

        public CreatorInfoResponse(String nickName, String pageName) {
            this.nickName = nickName;
            this.pageName = pageName;
        }

        public CreatorInfoResponse(Member member) {
            this(member.getNickname(), member.getPageName());
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class DonationInfoResponse {

        private String name;
        private Long amount;
        private String message;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy'/'MM'/'dd'/' HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime date;

        public DonationInfoResponse(String name, Long amount, String message, LocalDateTime date) {
            this.name = name;
            this.amount = amount;
            this.message = message;
            this.date = date;
        }

        public DonationInfoResponse(Donation donation, Payment payment) {
            this(donation.getName(), payment.getAmount(), donation.getMessage(), donation.getCreatedAt());
        }
    }
}
