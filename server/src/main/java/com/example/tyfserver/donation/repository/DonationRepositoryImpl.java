package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.member.domain.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

import static com.example.tyfserver.donation.domain.QDonation.donation;
import static com.example.tyfserver.member.domain.QMember.member;

public class DonationRepositoryImpl implements DonationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DonationRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long waitingTotalPoint(Long creatorId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(
                        donationReceiver(creatorId),
                        waitingForExchangeStatus()
                )
                .groupBy(donation.creator)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    @Override
    public Long exchangedTotalPoint(Long creatorId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(
                        donationReceiver(creatorId),
                        exchangedStatus()
                )
                .groupBy(donation.creator)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    @Override
    public List<Donation> findDonationsToExchange(Member creator, YearMonth exchangeOn) {
        return queryFactory
                .selectFrom(donation)
                .where(
                        donationReceiver(creator.getId()),
                        waitingForExchangeStatus(),
                        beforeStartOfNextMonth(exchangeOn)
                )
                .fetch();
    }

    @Override
    public List<Donation> find5NewestDonationsPage(Member creator, Long lastPageId) {
        return queryFactory
                .selectFrom(donation)
                .join(donation.donator, member)
                .fetchJoin()
                .where(
                        lessThanDonationId(lastPageId),
                        donationReceiver(creator.getId())
                )
                .orderBy(donation.id.desc())
                .limit(5)
                .fetch();
    }

    private BooleanExpression lessThanDonationId(long offset) {
        if (offset == 0) {
            return null;
        }
        return donation.id.lt(offset);
    }

    private BooleanExpression waitingForExchangeStatus() {
        return donation.status.eq(DonationStatus.WAITING_FOR_EXCHANGE);
    }

    private BooleanExpression exchangedStatus() {
        return donation.status.eq(DonationStatus.EXCHANGED);
    }

    private BooleanExpression donationReceiver(Long creatorId) {
        return donation.creator.id.eq(creatorId);
    }

    private BooleanExpression beforeStartOfNextMonth(YearMonth exchangeOn) {
        // 정산신청일자 다음달 1일 00:00 이전
        LocalDateTime startOfNextMonth = exchangeOn.plusMonths(1).atDay(1).atStartOfDay();
        return donation.createdAt.before(startOfNextMonth);
    }
}
