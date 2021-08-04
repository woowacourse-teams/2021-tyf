package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameRequest {

    @NotBlank
    @Length(min = 2, max = 20)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$")
    private String nickname;

    public NicknameRequest(String nickname) {
        this.nickname = nickname;
    }
}
