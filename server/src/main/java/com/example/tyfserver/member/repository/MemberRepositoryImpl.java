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

    // todo 쿼리 개선필요 - 14sec 걸림

    //     select
    //        member0_.nickname as col_0_0_,
    //        member0_.page_name as col_1_0_,
    //        member0_.profile_image as col_2_0_,
    //        member0_.bio as col_3_0_
    //    from
    //        member member0_
    //    left outer join
    //        donation receiveddo1_
    //            on member0_.id=receiveddo1_.creator_id
    //    inner join
    //        account account2_
    //            on member0_.account_id=account2_.id
    //            and (
    //                account2_.status=REGISTERED
    //            )
    //    group by
    //        member0_.id
    //    order by
    //        sum(receiveddo1_.point) desc limit ?
    public List<CurationsResponse> findCurations() {
        return queryFactory
                .select(
                        new QCurationsResponse(member.nickname, member.pageName, member.profileImage, member.bio))
                .from(member)
                .leftJoin(member.receivedDonations, donation)
                .innerJoin(member.account, account).on(member.account.status.eq(AccountStatus.REGISTERED))
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
