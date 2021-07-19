package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageNameValidationRequest {

    @Length(min = 3, max = 20)
    @NotBlank
    @Pattern(regexp = "^[a-z0-9_-]*$") //todo: 정규표현식 수정 필요
    private String pageName;

    public PageNameValidationRequest(String pageName) {
        this.pageName = pageName;
    }
}
