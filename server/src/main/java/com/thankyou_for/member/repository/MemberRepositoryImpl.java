package com.thankyou_for.member.repository;

import com.thankyou_for.member.domain.AccountStatus;
import com.thankyou_for.member.domain.Member;
import com.thankyou_for.member.dto.CurationsResponse;
import com.thankyou_for.member.dto.QCurationsResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.thankyou_for.donation.domain.QDonation.donation;
import static com.thankyou_for.member.domain.QAccount.account;
import static com.thankyou_for.member.domain.QMember.member;

public class MemberRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public List<CurationsResponse> findCurations() {
        return queryFactory
                .select(
                        new QCurationsResponse(member.nickname, member.pageName, member.profileImage, member.bio))
                .from(member)
                .leftJoin(member.receivedDonations, donation)
                .innerJoin(member.account, account).on(member.account.status.eq(AccountStatus.valueOf("REGISTERED")))
                .groupBy(member)
                .orderBy(donation.point.sum().desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    @Override
    public List<Member> findRequestingAccounts() {
        return queryFactory
                .selectFrom(member)
                .join(member.account, account).fetchJoin()
                .where(account.status.eq(AccountStatus.REQUESTING))
                .fetch();
    }
}
