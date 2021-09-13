package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageNameRequest {

    @Length(min = 3, max = 20)
    @NotBlank
    @Pattern(regexp = "^[a-z0-9][a-z0-9_-]+[a-z0-9]$")
    private String pageName;

    public PageNameRequest(String pageName) {
        this.pageName = pageName;
    }
}
