package com.thankyou_for.member.repository;

import com.thankyou_for.member.dto.ExchangeAmountDto;

import java.time.YearMonth;
import java.util.List;

public interface ExchangeQueryRepository {

    List<ExchangeAmountDto> calculateExchangeAmountFromDonation(YearMonth exchangeApproveOn);
}
