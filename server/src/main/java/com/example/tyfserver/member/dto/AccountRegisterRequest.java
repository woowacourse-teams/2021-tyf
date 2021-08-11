package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRegisterRequest {
    @NotBlank
    private String accountHolder;
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String bank;
    @NotNull
    private MultipartFile bankbookImage;

    public AccountRegisterRequest(String accountHolder, String accountNumber, MultipartFile bankbookImage, String bank) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.bankbookImage = bankbookImage;
        this.bank = bank;
    }
}