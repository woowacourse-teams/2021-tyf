package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRegisterRequest {
    private String accountHolder;
    private String account;
    private String bank;
    private MultipartFile bankbook;

    public AccountRegisterRequest(String accountHolder, String account, MultipartFile bankbookImage, String bank) {
        this.accountHolder = accountHolder;
        this.account = account;
        this.bankbook = bankbookImage;
        this.bank = bank;
    }
}
