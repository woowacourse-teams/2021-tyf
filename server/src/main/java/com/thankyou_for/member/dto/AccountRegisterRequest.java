package com.thankyou_for.member.dto;

import com.thankyou_for.payment.util.ResidentRegistrationNumber;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRegisterRequest {
    @NotBlank
    private String accountHolder;
    @NotBlank
    private String accountNumber;
    @ResidentRegistrationNumber
    private String residentRegistrationNumber;
    @NotBlank
    private String bank;

    public AccountRegisterRequest(String accountHolder, String accountNumber, String residentRegistrationNumber, String bank) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.residentRegistrationNumber = residentRegistrationNumber;
        this.bank = bank;
    }
}
