package com.example.tyfserver.donation.domain;

import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger amount;

    private String name;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Donation(BigInteger amount) {
        this.amount = amount;
    }
}
