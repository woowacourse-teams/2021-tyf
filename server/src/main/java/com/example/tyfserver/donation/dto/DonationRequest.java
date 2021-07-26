package com.example.tyfserver.donation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DonationRequest {

    @NotBlank
    private String impUid;
    @NotNull
    private Long merchantUid;

    public DonationRequest(String impUid, Long merchantUid) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
    }
}
