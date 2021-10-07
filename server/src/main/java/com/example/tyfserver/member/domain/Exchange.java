package com.example.tyfserver.member.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exchange extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long exchangeAmount;

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

    public Exchange(Member member) {
        this(0L, null, member);
    }

    @PrePersist
    private void exchangeOn() {
        if (Objects.isNull(exchangeOn)) {
            exchangeOn = YearMonth.now();
        }
    }

    public void registerExchangeAmount(Long exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public void toApproved() {
        status = ExchangeStatus.APPROVED;
    }

    public void toRejected() {
        status = ExchangeStatus.REJECTED;
    }
}
