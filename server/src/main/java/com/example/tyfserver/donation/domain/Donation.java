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

    public Donation(Long id, Long amount, Message message) {
        this.id = id;
        this.amount = amount;
        this.message = message;
    }

    public Donation(Long amount) {
        this(amount, Message.defaultMessage());
    }

    public Donation(Long amount, Message message) {
        this.amount = amount;
        this.message = message;
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

    public void hideNameAndMessageWhenSecret() {
        if (message.isSecret()) {
            message.hideNameAndMessage();
        }
    }
}
