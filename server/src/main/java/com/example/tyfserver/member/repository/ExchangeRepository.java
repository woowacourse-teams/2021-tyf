package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.domain.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    boolean existsByPageName(String pageName);
    void deleteByPageName(String pageName);
}
