package com.thankyou_for.member.domain;

import com.thankyou_for.common.domain.BaseTimeEntity;
import com.thankyou_for.member.exception.AccountAlreadyRegisteredException;
import com.thankyou_for.member.exception.AccountRequestingException;
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

    private String residentRegistrationNumber;

    private String bank;

    private String bankbookUrl; // todo 필요없어진 컬럼. 제거해야함.

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
