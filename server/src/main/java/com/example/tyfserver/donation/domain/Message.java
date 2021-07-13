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

    private boolean isPublic;

    public Message(String name, String message, boolean isPublic) {
        this.name = name;
        this.message = message;
        this.isPublic = isPublic;
    }
}
