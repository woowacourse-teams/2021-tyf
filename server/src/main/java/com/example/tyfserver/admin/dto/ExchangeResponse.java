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

    private Long exchangeAmount;

    private String accountNumber;

    private String nickname;

    private String pageName;

    private LocalDateTime createdAt;

    public ExchangeResponse(Exchange exchange, String decryptedAccountNumber) {
        this(
                exchange.getMember().getAccount().getAccountHolder(),
                exchange.getMember().getEmail(),
                exchange.getExchangeAmount(),
                decryptedAccountNumber,
                exchange.getMember().getNickname(),
                exchange.getMember().getPageName(),
                exchange.getCreatedAt()
        );
    }

    public ExchangeResponse(String name, String email, Long exchangeAmount, String accountNumber, String nickname, String pageName, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.exchangeAmount = exchangeAmount;
        this.accountNumber = accountNumber;
        this.nickname = nickname;
        this.pageName = pageName;
        this.createdAt = createdAt;
    }
}
