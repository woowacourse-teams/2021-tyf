package com.thankyou_for.member.repository;

import com.thankyou_for.member.domain.Exchange;
import com.thankyou_for.member.domain.ExchangeStatus;
import com.thankyou_for.member.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long>, ExchangeQueryRepository {

    List<Exchange> findByStatusAndMember(ExchangeStatus status, Member member);

    @EntityGraph(attributePaths = {"member", "member.account"})
    List<Exchange> findByStatus(ExchangeStatus status);

    Boolean existsByStatusAndMember(ExchangeStatus status, Member member);
}
