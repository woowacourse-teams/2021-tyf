package com.example.tyfserver.member.repository;

import com.example.tyfserver.common.config.JpaAuditingConfig;
import com.example.tyfserver.member.domain.Exchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class ExchangeRepositoryTest {

    @Autowired
    private ExchangeRepository exchangeRepository;

    private Exchange exchange;

    @BeforeEach
    void setUp() {
        exchange = new Exchange("승윤", "tyf@gmail.com", 1000L, "123-123-123", "nickname", "pagename");
        exchangeRepository.save(exchange);
    }

    @AfterEach
    void tearDown() {
        exchangeRepository.delete(exchange);
    }

    @Test
    @DisplayName("페이지 네임으로 정산존재 확인")
    public void existsByPageName() {
        boolean result = exchangeRepository.existsByPageName(exchange.getPageName());
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("페이지 네임으로 정산존재 확인 - 못찾은 경우")
    public void existsByPageNameNotFound() {
        boolean result = exchangeRepository.existsByPageName("nope");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("페이지 네임으로 정산 삭제")
    public void deleteByPageName() {
        boolean result = exchangeRepository.existsByPageName(exchange.getPageName());
        assertThat(result).isTrue();

        exchangeRepository.deleteByPageName(exchange.getPageName());
        result = exchangeRepository.existsByPageName(exchange.getPageName());
        assertThat(result).isFalse();
    }
}