package com.thankyou_for.auth.dto;

import lombok.Getter;

@Getter
public class IdAndEmail {

    private Long id;
    private String email;

    public IdAndEmail(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
