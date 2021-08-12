package com.example.tyfserver.member.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exchange extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private Long exchangeAmount;

    private String accountNumber;

    private String nickname;

    private String pageName;

    public Exchange(String name, String email, Long exchangeAmount, String accountNumber, String nickname, String pageName) {
        this.name = name;
        this.email = email;
        this.exchangeAmount = exchangeAmount;
        this.accountNumber = accountNumber;
        this.nickname = nickname;
        this.pageName = pageName;
    }
}
