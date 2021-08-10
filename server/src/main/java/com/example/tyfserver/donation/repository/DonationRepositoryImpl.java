package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.DonationStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static com.example.tyfserver.donation.domain.QDonation.donation;
import static com.example.tyfserver.payment.domain.QPayment.payment;

public class DonationRepositoryImpl implements DonationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DonationRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public Long exchangeablePoint(Long memberId, LocalDateTime now, long exchangeableDayLimit) {
        Long result = queryFactory
                .select(payment.amount.sum())
                .from(donation)
                .join(donation.payment, payment)
                .where(validateStatus(memberId), donation.createdAt.before(now.minusDays(exchangeableDayLimit)))
                .groupBy(donation.member)
                .fetchOne();

        if (result == null) {
            return 0L;
        }
        return result;
    }

    public Long exchangedTotalPoint(Long memberId) {
        Long result = queryFactory
                .select(payment.amount.sum())
                .from(donation)
                .join(donation.payment, payment)
                .where(exchangedStatus(memberId))
                .groupBy(donation.member)
                .fetchOne();

        if (result == null) {
            return 0L;
        }
        return result;
    }

    private BooleanExpression validateStatus(Long memberId) {
        return donation.member.id.eq(memberId)
                .and(donation.status.ne(DonationStatus.EXCHANGED))
                .and(donation.status.ne(DonationStatus.CANCELLED));
    }

    private BooleanExpression exchangedStatus(Long memberId) {
        return donation.member.id.eq(memberId)
                .and(donation.status.eq(DonationStatus.EXCHANGED));
    }
}
