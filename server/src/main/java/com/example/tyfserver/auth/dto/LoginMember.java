package com.example.tyfserver.auth.dto;

import lombok.Getter;

@Getter
public class LoginMember {

    private String email;

    public LoginMember(String email) {
        this.email = email;
    }
}
