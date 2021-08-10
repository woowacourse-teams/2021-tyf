package com.example.tyfserver.member.repository;

import com.example.tyfserver.admin.dto.QRequestingAccountResponse;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.member.domain.AccountStatus;
import com.example.tyfserver.member.dto.CurationsResponse;
import com.example.tyfserver.member.dto.QCurationsResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.tyfserver.donation.domain.QDonation.donation;
import static com.example.tyfserver.member.domain.QAccount.account;
import static com.example.tyfserver.member.domain.QMember.member;
import static com.example.tyfserver.payment.domain.QPayment.payment;

public class MemberRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public List<CurationsResponse> findCurations() {
        return queryFactory
                .select(
                        new QCurationsResponse(member.nickname, payment.amount.sum(), member.pageName, member.profileImage, member.bio))
                .from(member)
                .leftJoin(member.donations, donation)
                .leftJoin(donation.payment, payment)
                .groupBy(member.nickname)
                .orderBy(payment.amount.sum().desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    @Override
    public List<RequestingAccountResponse> findRequestingAccounts() {
        return queryFactory.select(
                new QRequestingAccountResponse(member.id, member.email, member.nickname, member.pageName, member.account.accountHolder,
                        account.accountNumber, account.bank, account.bankbookUrl))
                .from(member)
                .innerJoin(member.account, account)
                .where(account.status.eq(AccountStatus.REQUESTING))
                .fetch();

    }
}
