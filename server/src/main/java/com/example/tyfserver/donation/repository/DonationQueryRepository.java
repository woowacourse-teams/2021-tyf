package com.example.tyfserver.donation.repository;

public interface DonationQueryRepository {

    Long currentPoint(Long memberId);

    Long exchangedTotalPoint(Long memberId);
}
