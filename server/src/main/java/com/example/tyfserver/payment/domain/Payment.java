package com.example.tyfserver.payment.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.donation.domain.Donation;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Long creatorId;

    @OneToOne(fetch = FetchType.LAZY)
    private Donation donation;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status = PaymentStatus.READY;

    public Payment(Long amount, String email, Long creatorId) {
        this.amount = amount;
        this.email = email;
        this.creatorId = creatorId;
    }

}
