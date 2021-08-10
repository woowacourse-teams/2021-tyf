package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.member.domain.Exchange;
import com.example.tyfserver.member.repository.ExchangeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Test
    @DisplayName("정산 목록 조회")
    public void exchangeList() {
        Exchange exchange1 = new Exchange(12000L, "123-123-123", "nickname1", "pagename1");
        Exchange exchange2 = new Exchange(21000L, "456-456-456", "nickname2", "pagename2");
        Exchange exchange3 = new Exchange(31000L, "789-789-789", "nickname3", "pagename3");
        exchangeRepository.save(exchange1);
        exchangeRepository.save(exchange2);
        exchangeRepository.save(exchange3);

        List<ExchangeResponse> responses = adminService.exchangeList();
        assertThat(responses.get(0)).usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(new ExchangeResponse(12000L, "123-123-123", "nickname1", "pagename1", LocalDateTime.now()));
    }
}