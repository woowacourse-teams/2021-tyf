package com.thankyou_for.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBioUpdateRequest {

    @NotBlank
    @Length(max = 500)
    private String bio;

    public MemberBioUpdateRequest(String bio) {
        this.bio = bio;
    }
}
