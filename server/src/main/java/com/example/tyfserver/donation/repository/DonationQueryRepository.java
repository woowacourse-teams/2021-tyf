package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.member.domain.Member;

import java.time.YearMonth;
import java.util.List;

public interface DonationQueryRepository {

    Long currentPoint(Long memberId);

    Long exchangedTotalPoint(Long memberId);

    List<Donation> findDonationsToExchange(Member member, YearMonth exchangeOn);
}
