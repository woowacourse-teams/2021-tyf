package com.example.tyfserver.donation.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.donation.exception.DonationAlreadyCancelledException;
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

    public final static long exchangeableDayLimit = 7;

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

    @Enumerated(value = EnumType.STRING)
    private DonationStatus status = DonationStatus.REFUNDABLE;

    public Donation(Long id, Payment payment, Message message) {
        this.id = id;
        this.payment = payment;
        this.message = message;
    }

    public Donation(Payment payment, Message message) {
        this(null, payment, message);
    }

    public Donation(Payment payment) {
        this(payment, Message.defaultMessage());
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

    public void updateStatus(DonationStatus updatedStatus) {
        this.status = updatedStatus;
    }

    public void cancel() {
        status = DonationStatus.CANCELLED;
        member.reducePoint(this.getAmount());
    }

    public void exchanged() {
        status = DonationStatus.EXCHANGED;
        member.reducePoint(this.getAmount());
    }

    public void validateIsNotCancelled() {
        if (status == DonationStatus.CANCELLED) {
            throw new DonationAlreadyCancelledException();
        }
    }
}
