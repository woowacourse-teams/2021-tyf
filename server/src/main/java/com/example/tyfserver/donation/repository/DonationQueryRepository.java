package com.example.tyfserver.donation.repository;

import java.time.LocalDateTime;

public interface DonationQueryRepository {
    Long exchangeablePoint(Long memberId, LocalDateTime now, long exchangeableDayLimit);

    Long exchangedTotalPoint(Long memberId);
}
