package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.dto.ExchangeAmountDto;

import java.time.YearMonth;
import java.util.List;

public interface ExchangeQueryRepository {
    List<ExchangeAmountDto> calculateExchangeAmountFromDonation(YearMonth exchangeOn);
}
