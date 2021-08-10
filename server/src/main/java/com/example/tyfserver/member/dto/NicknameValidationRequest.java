package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameValidationRequest {

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$") //todo: 정규표현식 수정 필요
    private String nickname;

    public NicknameValidationRequest(String nickname) {
        this.nickname = nickname;
    }
}
