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

    @Embedded
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Donation(Long amount) {
        this(amount, new Message("익명", "당신을 응원합니다.", false));
    }

    public Donation(Long amount, Message message) {
        this.amount = amount;
        this.message = message;
    }

    public void to(final Member member) {
        this.member = member;
    }

    public void addMessage(String name, String message, boolean secret) {
        this.message = new Message(name, message, secret);
    }

    public void hideNameAndMessage() {
        message.hideNameAndMessage();
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
