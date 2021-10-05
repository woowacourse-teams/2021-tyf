package com.example.tyfserver.admin.dto;

import com.example.tyfserver.member.domain.Exchange;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ExchangeResponse {

    private String name;
    private String email;
    private String nickname;
    private String pageName;
    private Long exchangeAmount;
    private LocalDateTime createdAt;
    private String accountNumber;

    public ExchangeResponse(Exchange exchange, String decryptedAccountNumber) {
        this(
                exchange.getMember().getAccount().getAccountHolder(),
                exchange.getMember().getEmail(),
                exchange.getMember().getNickname(),
                exchange.getMember().getPageName(),
                exchange.getExchangeAmount(),
                exchange.getCreatedAt(),
                decryptedAccountNumber
        );
    }

    public ExchangeResponse(String name, String email, String nickname, String pageName, Long exchangeAmount, LocalDateTime createdAt, String accountNumber) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.pageName = pageName;
        this.exchangeAmount = exchangeAmount;
        this.createdAt = createdAt;
        this.accountNumber = accountNumber;
    }
}
