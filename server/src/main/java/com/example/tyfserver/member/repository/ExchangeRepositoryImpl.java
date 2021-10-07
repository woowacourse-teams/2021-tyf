package com.example.tyfserver.member.repository;

import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.member.domain.ExchangeStatus;
import com.example.tyfserver.member.dto.ExchangeAmountDto;
import com.example.tyfserver.member.dto.QExchangeAmountDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static com.example.tyfserver.donation.domain.QDonation.donation;
import static com.example.tyfserver.member.domain.QExchange.exchange;

public class ExchangeRepositoryImpl implements ExchangeQueryRepository{

    private final JPAQueryFactory queryFactory;

    public ExchangeRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ExchangeAmountDto> calculateExchangeAmountFromDonation(YearMonth exchangeOn) {
        return queryFactory
                .select(new QExchangeAmountDto(exchange.id, donation.point.sum()))
                .from(exchange)
                .leftJoin(exchange.member.receivedDonations, donation)
                .groupBy(exchange.id)
                .where(
                        exchange.status.eq(ExchangeStatus.WAITING),
                        waitingForExchangeStatus(),
                        beforeStartOfNextMonth(exchangeOn)
                )
                .fetch();
    }

    private BooleanExpression waitingForExchangeStatus() {
        return donation.status.eq(DonationStatus.WAITING_FOR_EXCHANGE);
    }

    private BooleanExpression beforeStartOfNextMonth(YearMonth exchangeOn) {
        // 정산신청일자 다음달 1일 00:00 이전
        LocalDateTime startOfNextMonth = exchangeOn.plusMonths(1).atDay(1).atStartOfDay();
        return donation.createdAt.before(startOfNextMonth);
    }
}
