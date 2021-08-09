package com.example.tyfserver.member.dto;

import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoResponse {
    private String status;
    private String name;
    private String bank;
    private String account;

    private AccountInfoResponse(String status, String name, String bank, String account) {
        this.status = status;
        this.name = name;
        this.bank = bank;
        this.account = account;
    }

    public static AccountInfoResponse of(Account account) {
        return new AccountInfoResponse(account.getStatus().name(), account.getName(), account.getBank(), account.getAccountNumber());
    }
}
