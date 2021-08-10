package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.DonationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface DonationQueryRepository {
    Long exchangeablePoint(Long memberId);
    Long exchangedTotalPoint(Long memberId);
}
