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

    private String bank;

    private String bankbookUrl;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status = AccountStatus.UNREGISTERED;

    public Account(String accountHolder, String accountNumber, String bankbookUrl, String bank) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.bankbookUrl = bankbookUrl;
        this.bank = bank;
    }

    public void register(String accountHolder, String accountNumber, String bank, String bankbookUrl) {
        validateRegisterAccount();
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
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

    private void validateRegisterAccount() {
        if (this.status == AccountStatus.REGISTERED) {
            throw new AccountAlreadyRegisteredException();
        }

        if (this.status == AccountStatus.REQUESTING) {
            throw new AccountRequestingException();
        }
    }
}