package com.example.tyfserver.member.dto;

import com.example.tyfserver.member.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRegisterRequest {
    private String name;
    private String account;
    private String bank;
    private MultipartFile bankbook;

    public AccountRegisterRequest(String name, String account, MultipartFile bankbook, String bank) {
        this.name = name;
        this.account = account;
        this.bankbook = bankbook;
        this.bank = bank;
    }
}
