package com.example.tyfserver.donation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    public static final String DEFAULT_NAME = "익명인";
    public static final String DEFAULT_MESSAGE = "당신을 응원합니다.";
    public static final String SECRET_NAME = "익명인";
    public static final String SECRET_MESSAGE = "비공개 메세지입니다.";

    private String name;

    private String message;

    private boolean secret;

    public Message(String name, String message, boolean secret) {
        this.name = name;
        this.message = message;
        this.secret = secret;
    }

    public static Message defaultMessage() {
        return new Message(Message.DEFAULT_NAME, Message.DEFAULT_MESSAGE, false);
    }
}
