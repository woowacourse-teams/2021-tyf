package com.example.tyfserver.donation.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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
    @JoinColumn(name = "donator_id")
    private Member donator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Member creator;

    @Enumerated(value = EnumType.STRING)
    private DonationStatus status = DonationStatus.WAITING_FOR_EXCHANGE;

    public Donation(Message message, long point) {
        this(null, message, point);
    }

    public Donation(Long id, Message message, long point) {
        this.id = id;
        this.message = message;
        this.point = point;
    }

    public void donate(Member donator, Member creator) {
        donator.donateDonation(this);
        creator.receiveDonation(this);
    }

    public void to(final Member creator) {
        this.creator = creator;
    }

    public void from(final Member donator) {
        this.donator = donator;
    }

    public void addMessage(Message message) {
        this.message = message;
    }

    public void toExchanged() {
        status = DonationStatus.EXCHANGED;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return Objects.equals(id, donation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
