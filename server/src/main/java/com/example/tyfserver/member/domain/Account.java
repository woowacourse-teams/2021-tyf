package com.example.tyfserver.member.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.member.exception.AccountRegisteredException;
import com.example.tyfserver.member.exception.AccountRequestingException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor()
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String accountNumber;

    private String bankbookUrl;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;

    public Account(String name, String accountNumber, String bankbookUrl) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bankbookUrl = bankbookUrl;
    }

    public void register(Account account) {
        validateRegisterAccount();
        this.accountNumber = account.accountNumber;
        this.name = account.name;
        this.bankbookUrl = bankbookUrl;
        this.status = AccountStatus.REQUESTING;
    }

    private void validateRegisterAccount() {
        if (this.status == AccountStatus.REGISTERED) {
            throw new AccountRegisteredException();
        }

        if (this.status == AccountStatus.REQUESTING) {
            throw new AccountRequestingException();
        }
    }
}
