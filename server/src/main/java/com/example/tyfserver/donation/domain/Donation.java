package com.example.tyfserver.donation.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Donation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Donation(Long id, Payment payment, Message message) {
        this.id = id;
        this.payment = payment;
        this.message = message;
    }

    public Donation(Payment payment) {
        this(payment, Message.defaultMessage());
    }

    public Donation(Payment payment, Message message) {
        this.payment = payment;
        this.message = message;
    }

    public void to(final Member member) {
        this.member = member;
    }

    public void addMessage(Message message) {
        this.message = message;
    }

    public String getName() {
        return message.getName();
    }

    public String getMessage() {
        return message.getMessage();
    }

    public boolean isSecret() {
        return message.isSecret();
    }

    public Long getAmount() {
        return payment.getAmount();
    }
}
