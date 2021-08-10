package com.example.tyfserver.member.dto;

import com.example.tyfserver.member.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoResponse {
    private String status;
    private String accountHolder;
    private String bank;
    private String accountNumber;

    private AccountInfoResponse(String status, String accountHolder, String bank, String accountNumber) {
        this.status = status;
        this.accountHolder = accountHolder;
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public static AccountInfoResponse of(Account account) {
        return new AccountInfoResponse(account.getStatus().name(), account.getAccountHolder(), account.getBank(), account.getAccountNumber());
    }
}
