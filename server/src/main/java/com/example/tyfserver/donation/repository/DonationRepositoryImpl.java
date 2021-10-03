package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.member.domain.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

import static com.example.tyfserver.donation.domain.QDonation.donation;

public class DonationRepositoryImpl implements DonationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DonationRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long currentPoint(Long memberId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(currentStatus(memberId))
                .groupBy(donation.creator)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    @Override
    public Long exchangedTotalPoint(Long memberId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(exchangedStatus(memberId))
                .groupBy(donation.creator)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    private BooleanExpression currentStatus(Long memberId) {
        return donation.creator.id.eq(memberId)
                .and(donation.status.eq(DonationStatus.WAITING_FOR_EXCHANGE));
    }

    private BooleanExpression exchangedStatus(Long memberId) {
        return donation.creator.id.eq(memberId)
                .and(donation.status.eq(DonationStatus.EXCHANGED));
    }

    @Override
    public List<Donation> findDonationsToExchange(Member creator, YearMonth exchangeOn) {
        return queryFactory
                .select(donation)
                .from(donation)
                .where(
                        donation.creator.eq(creator),
                        donation.status.eq(DonationStatus.WAITING_FOR_EXCHANGE),
                        // 정산신청일자 다음달 1월1일 00:00 이전
                        donation.createdAt.before(exchangeOn.plusMonths(1).atDay(1).atStartOfDay())
                )
                .fetch();
    }
}
