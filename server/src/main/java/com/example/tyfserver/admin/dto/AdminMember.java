package com.example.tyfserver.admin.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminMember {

    private String name; // todo: 전달해줄 값이 있을까?

    public AdminMember(String name) {
        this.name = name;
    }
}
