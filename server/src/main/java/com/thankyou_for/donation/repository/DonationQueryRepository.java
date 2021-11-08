package com.thankyou_for.donation.repository;

import com.thankyou_for.donation.domain.Donation;
import com.thankyou_for.member.domain.Member;

import java.time.YearMonth;
import java.util.List;

public interface DonationQueryRepository {

    Long waitingTotalPoint(Long creatorId);

    Long exchangedTotalPoint(Long creatorId);

    List<Donation> findDonationsToExchange(Member creator, YearMonth exchangeOn);

    Long calculateExchangeAmountFromDonation(Member creator, YearMonth exchangeOn);
}
