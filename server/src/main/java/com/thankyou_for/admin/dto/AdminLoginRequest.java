package com.thankyou_for.admin.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminLoginRequest {

    private String id;
    private String password;

    public AdminLoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
