package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlValidationRequest {

    @Length(min = 1, max = 50, message = "url의 길이는 1이상 50이하 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]", message = "알파벳, 숫자만 가능합니다.")
    private String urlName;
}
