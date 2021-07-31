package com.example.tyfserver.payment.repository;

import com.example.tyfserver.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByMerchantUid(UUID merchantUid);
}
