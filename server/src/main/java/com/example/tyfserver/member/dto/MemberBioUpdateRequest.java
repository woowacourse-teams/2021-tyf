package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBioUpdateRequest {

    @NotBlank
    private String bio;

    public MemberBioUpdateRequest(String bio) {
        this.bio = bio;
    }
}
