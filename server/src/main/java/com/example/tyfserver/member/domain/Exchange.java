package com.example.tyfserver.member.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.YearMonth;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exchange extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long exchangeAmount;

    // 정산신청 연/월. 엔티티를 생성하는 시점(createdAt)과 정산하고자하는 달은 다를수 있다고 생각함.
    private YearMonth exchangeOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private ExchangeStatus status = ExchangeStatus.WAITING;

    public Exchange(Long id, Long exchangeAmount, YearMonth exchangeOn, Member member) {
        this.id = id;
        this.exchangeAmount = exchangeAmount;
        this.exchangeOn = exchangeOn;
        this.member = member;
    }

    public Exchange(Long exchangeAmount, YearMonth exchangeOn, Member member) {
        this(null, exchangeAmount, exchangeOn, member);
    }

    public void toApproved() {
        status = ExchangeStatus.APPROVED;
    }

    public void toRejected() {
        status = ExchangeStatus.REJECTED;
    }
}
