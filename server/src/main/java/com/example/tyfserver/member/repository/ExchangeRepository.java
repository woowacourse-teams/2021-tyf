package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.domain.Exchange;
import com.example.tyfserver.member.domain.ExchangeStatus;
import com.example.tyfserver.member.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findByStatusAndMember(ExchangeStatus status, Member member);

    @EntityGraph(attributePaths = {"member", "member.account"})
    List<Exchange> findByStatus(ExchangeStatus status);

    Boolean existsByStatusAndMember(ExchangeStatus status, Member member);
}
