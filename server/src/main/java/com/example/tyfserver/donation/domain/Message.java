package com.example.tyfserver.donation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    public static final String DEFAULT_MESSAGE = "당신을 응원합니다.";
    public static final String SECRET_NAME = "익명인";
    public static final String SECRET_MESSAGE = "비공개 메세지입니다.";

    // todo 프론트와 협의 필요:
    //  - 비공개 메시지의 pageName은 뭐로 할지
    //  - 뭐로 주던간에 프론트에서는 조건문 걸어서 비공개메시지는 페이지로 링크 못가게 처리해야할듯
    public static final String SECRET_PAGE_NAME = "비공개";


    private String name;

    private String message;

    private boolean secret;

    public Message(String name) {
        this(name, DEFAULT_MESSAGE, false);
    }

    public Message(String name, String message, boolean secret) {
        this.name = name;
        this.message = message;
        this.secret = secret;
    }
}
