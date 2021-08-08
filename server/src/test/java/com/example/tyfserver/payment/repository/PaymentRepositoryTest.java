package com.example.tyfserver.payment.repository;

import com.example.tyfserver.common.config.JpaAuditingConfig;
import com.example.tyfserver.payment.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    private UUID uuid = UUID.randomUUID();
    private Payment payment = new Payment(1000L, "email@gmail.com", "pageName", uuid);

    @BeforeEach
    void setUp() {
        paymentRepository.save(payment);
    }

    @Test
    @DisplayName("findByMerchantId")
    public void findByMerchantIdTest() {
        //given&then
        Payment payment = paymentRepository.findByMerchantUid(uuid).get();

        //then
        assertThat(payment.getId()).isEqualTo(payment.getId());
    }
}