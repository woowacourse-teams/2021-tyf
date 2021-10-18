package com.example.tyfserver.member.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.member.exception.AccountAlreadyRegisteredException;
import com.example.tyfserver.member.exception.AccountRequestingException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountHolder;

    @Column(unique = true)
    private String accountNumber;

    @Column(unique = true) //todo: 현재 unique 속성을 걸면 이전 계정들이 다 문제가 생기는데 이 부분은 빼고 생각해야할까? (batch쪽도 문제 생김)
    private String residentRegistrationNumber;

    private String bank;

    private String bankbookUrl;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status = AccountStatus.UNREGISTERED;

    public Account(String accountHolder, String accountNumber, String residentRegistrationNumber, String bank, String bankbookUrl, AccountStatus status) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.residentRegistrationNumber = residentRegistrationNumber;
        this.bank = bank;
        this.bankbookUrl = bankbookUrl;
        this.status = status;
    }

    public Account(String accountHolder, String accountNumber, String residentRegistrationNumber, String bankbookUrl, String bank) {
        this(accountHolder, accountNumber, residentRegistrationNumber, bank, bankbookUrl, AccountStatus.UNREGISTERED);
    }

    public void register(String accountHolder, String accountNumber, String residentRegistrationNumber, String bank, String bankbookUrl) {
        validateRegisterAccount();
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.residentRegistrationNumber = residentRegistrationNumber;
        this.bank = bank;
        this.bankbookUrl = bankbookUrl;
        this.status = AccountStatus.REQUESTING;
    }

    public void approve() {
        this.status = AccountStatus.REGISTERED;
    }

    public void reject() {
        this.status = AccountStatus.REJECTED;
    }

    public boolean isAccountNumberNotEmpty() {
        return accountNumber != null && !accountNumber.isBlank();
    }

    private void validateRegisterAccount() {
        if (this.status == AccountStatus.REGISTERED) {
            throw new AccountAlreadyRegisteredException();
        }

        if (this.status == AccountStatus.REQUESTING) {
            throw new AccountRequestingException();
        }
    }
}
