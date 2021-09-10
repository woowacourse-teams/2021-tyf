package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.domain.AccountStatus;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.CurationsResponse;
import com.example.tyfserver.member.dto.QCurationsResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.tyfserver.donation.domain.QDonation.donation;
import static com.example.tyfserver.member.domain.QAccount.account;
import static com.example.tyfserver.member.domain.QMember.member;

public class MemberRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public List<CurationsResponse> findCurations() {
        return queryFactory
                .select(
                        new QCurationsResponse(member.nickname, donation.point.sum(), member.pageName, member.profileImage, member.bio))
                .from(member)
                .leftJoin(member.donations, donation)
                .groupBy(member.nickname)
                .orderBy(donation.point.sum().desc()) // todo 개선 필요 : 도네이션 합계 쿼리시 모든 도네이션을 계산중임. CANCELLED 같은 애들은 빼고 정상적인 도네이션만 쿼리 필요.
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
