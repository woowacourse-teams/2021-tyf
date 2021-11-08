package com.thankyou_for.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeAmountDto {
    private Long exchangeId;
    private Long exchangeAmount;

    @QueryProjection
    public ExchangeAmountDto(Long exchangeId, Long exchangeAmount) {
        this.exchangeId = exchangeId;
        this.exchangeAmount = exchangeAmount;
    }
}
