package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.DonationStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Objects;

import static com.example.tyfserver.donation.domain.QDonation.donation;

public class DonationRepositoryImpl implements DonationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DonationRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public Long exchangeablePoint(Long memberId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(exchangeableStatus(memberId))
                .groupBy(donation.creator)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    public Long exchangedTotalPoint(Long memberId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(exchangedStatus(memberId))
                .groupBy(donation.creator)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    public Long currentPoint(Long memberId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(currentStatus(memberId))
                .groupBy(donation.creator)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    private BooleanExpression exchangeableStatus(Long memberId) {
        return donation.creator.id.eq(memberId)
                .and(donation.status.eq(DonationStatus.EXCHANGEABLE));
    }

    private BooleanExpression exchangedStatus(Long memberId) {
        return donation.creator.id.eq(memberId)
                .and(donation.status.eq(DonationStatus.EXCHANGED));
    }

    private BooleanExpression currentStatus(Long memberId) {
        return donation.creator.id.eq(memberId)
                .and(donation.status.ne(DonationStatus.CANCELLED))
                .and(donation.status.ne(DonationStatus.EXCHANGED));
    }
}
