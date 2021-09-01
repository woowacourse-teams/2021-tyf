package com.example.tyfserver.donation.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.donation.exception.DonationAlreadyCancelledException;
import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Donation extends BaseTimeEntity {

    public final static long EXCHANGEABLE_DAY_LIMIT = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Message message;

    private long point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private DonationStatus status = DonationStatus.REFUNDABLE;

    public Donation(Long id, Message message, long point) {
        this.id = id;
        this.message = message;
        this.point = point;
    }

    public Donation(long point) {
        this.id = null;
        this.message = Message.defaultMessage();
        this.point = point;
    }

    public Donation(Message message) {
        this(null, message, 0L);
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

    public void toCancelled() {
        status = DonationStatus.CANCELLED;
    }

    public void toExchanged() {
        status = DonationStatus.EXCHANGED;
    }

    public void toExchangeable() {
        status = DonationStatus.EXCHANGEABLE;
    }

    public void validateIsNotCancelled() {
        if (status == DonationStatus.CANCELLED) {
            throw new DonationAlreadyCancelledException();
        }
    }

    public boolean isNotRefundable() {
        return status != DonationStatus.REFUNDABLE;
    }
}
