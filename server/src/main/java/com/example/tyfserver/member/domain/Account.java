package com.example.tyfserver.member.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String account;

    private String bankbookUrl;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status = AccountStatus.UNREGISTERED;

    public boolean checkAccountStatus(AccountStatus expect) {
        return this.status == expect;
    }

    public Account(String name, String account, String bankbookUrl) {
        this.name = name;
        this.account = account;
        this.bankbookUrl = bankbookUrl;
    }
}
