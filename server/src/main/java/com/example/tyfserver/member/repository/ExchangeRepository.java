package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.domain.Exchange;
import com.example.tyfserver.member.domain.ExchangeStatus;
import com.example.tyfserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

//    @EntityGraph(attributePaths = {"member"}) // todo N+1 문제 확인 필요
    List<Exchange> findByStatusAndMember(ExchangeStatus status, Member member);

    List<Exchange> findByStatus(ExchangeStatus status);
}
