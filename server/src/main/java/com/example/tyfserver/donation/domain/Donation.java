package com.example.tyfserver.donation.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.member.domain.Member;
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

    private Long amount;

    private String name = "익명";

    private String message = "당신을 응원합니다.";

    private boolean isPublic = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Donation(Long amount) {
        this.amount = amount;
    }

    public void to(final Member member) {
        this.member = member;
    }

    public void addMessage(String name, String message, boolean isPublic) {
        this.name = name;
        this.message = message;
        this.isPublic = isPublic;
    }
}
