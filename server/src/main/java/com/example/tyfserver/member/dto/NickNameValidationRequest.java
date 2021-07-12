package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NickNameValidationRequest {

    @Length(min = 1, max = 20, message = "닉네임은 1글자 이상 20글자 이하여야 합니다.")
    @Pattern(regexp = "[가-힣a-zA-Z0-9]", message = "한글, 알파벳, 숫자만 가능합니다.")
    private String nickName;
}
