package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.domain.AccountStatus;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.CurationsResponse;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.tyfserver.donation.domain.QDonation.donation;
import static com.example.tyfserver.member.domain.QAccount.account;
import static com.example.tyfserver.member.domain.QMember.member;
import static com.example.tyfserver.payment.domain.QPayment.payment;

public class MemberRepositoryImpl implements MemberQueryRepository {

    private final EntityManager em;

    @Override
    public List<CurationsResponse> findCurations() {
        return em.createQuery(
                "select new com.example.tyfserver.member.dto.CurationsResponse(" +
                        "           d.member.nickname, sum(d.payment.amount), d.member.pageName, d.member.profileImage, d.member.bio) " +
                        "from Donation d " +
                        "join d.member " +
                        "group by d.member " +
                        "order by sum(d.payment.amount) desc ",
                CurationsResponse.class
        )
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
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
