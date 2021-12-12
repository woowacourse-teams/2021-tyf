package com.thankyou_for.auth.dto;

import lombok.Getter;

@Getter
public class LoginMember {

    private Long id;
    private String email;

    public LoginMember(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
