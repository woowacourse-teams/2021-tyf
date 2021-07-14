package com.example.tyfserver.donation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    private String name;

    private String message;

    private boolean secret;

    public Message(String name, String message, boolean secret) {
        this.name = name;
        this.message = message;
        this.secret = secret;
    }

    public void hideNameAndMessage() {
        name = "익명";
        message = "비공개 메세지입니다.";
    }
}
