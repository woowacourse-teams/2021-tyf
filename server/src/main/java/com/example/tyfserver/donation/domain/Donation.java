package com.example.tyfserver.donation.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private DonationStatus status = DonationStatus.WAITING_FOR_EXCHANGE;

    public Donation(Message message, long point) {
        this(null, message, point);
    }

    public Donation(Message message, long point, LocalDateTime createdAt) {
        super(createdAt);
        this.message = message;
        this.point = point;
    }

    public Donation(Long id, Message message, long point) {
        this.id = id;
        this.message = message;
        this.point = point;
    }

    public void to(final Member member) {
        this.member = member;
    }

    public void addMessage(Message message) {
        this.message = message;
    }

    // todo 테스트 코드에서만 사용중
    public void toCancelled() {
        status = DonationStatus.CANCELLED;
    }

    public void toExchanged() {
        status = DonationStatus.EXCHANGED;
    }

    // todo 테스트 코드에서만 사용중 : 스케줄러 적용시 사용
    public void toExchangeable() {
        status = DonationStatus.EXCHANGEABLE;
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
}
