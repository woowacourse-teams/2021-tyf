package com.example.tyfserver.donation.repository;

public interface DonationQueryRepository {
    Long exchangeablePoint(Long memberId);
    Long exchangedTotalPoint(Long memberId);
    Long currentPoint(Long memberId);
}
