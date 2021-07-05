package com.example.tyfserver.donation.domain;

import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private String name = "익명";

    private String message = "당신을 응원합니다.";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Donation(Long amount) {
        this.amount = amount;
    }

    public void to(final Member member) {
        this.member = member;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return this.name;
    }

    public String message() {
        return this.message;
    }

    public void addMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
