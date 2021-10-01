package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.member.domain.Member;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.*;
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

    public Long exchangeablePoint(Long memberId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(exchangeableStatus(memberId))
                .groupBy(donation.member)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    public Long exchangedTotalPoint(Long memberId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(exchangedStatus(memberId))
                .groupBy(donation.member)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    public Long currentPoint(Long memberId) {
        Long result = queryFactory
                .select(donation.point.sum())
                .from(donation)
                .where(currentStatus(memberId))
                .groupBy(donation.member)
                .fetchOne();

        return Objects.requireNonNullElse(result, 0L);
    }

    private BooleanExpression exchangeableStatus(Long memberId) {
        return donation.member.id.eq(memberId)
                .and(donation.status.eq(DonationStatus.EXCHANGEABLE));
    }

    private BooleanExpression exchangedStatus(Long memberId) {
        return donation.member.id.eq(memberId)
                .and(donation.status.eq(DonationStatus.EXCHANGED));
    }

    private BooleanExpression currentStatus(Long memberId) {
        return donation.member.id.eq(memberId)
                .and(donation.status.ne(DonationStatus.CANCELLED))
                .and(donation.status.ne(DonationStatus.EXCHANGED));
    }

    @Override
    public List<Donation> findDonationsToExchange(Member creator, YearMonth exchangeOn) {
        return queryFactory
                .select(donation)
                .from(donation)
                .where(toExchange(creator, exchangeOn))
                .fetch();
    }

    private BooleanExpression toExchange(Member creator, YearMonth exchangeOn) {
        return donation.member.eq(creator)
                .and(donation.status.eq(DonationStatus.WAITING_FOR_EXCHANGE))
                .and(donation.createdAt.yearMonth().loe(getYearMonth(exchangeOn)));
//                .and(donation.createdAt.yearMonth().loe(exchangeOn));
    }

    private NumberExpression<Integer> getYearMonth(YearMonth exchangeOn) {
        // 제대로 동작안함 "{0}"의 문제일수도?
        return Expressions.dateTemplate(YearMonth.class, "{0}", exchangeOn).yearMonth();
    }
}
