package com.example.tyfserver.payment.repository;

import com.example.tyfserver.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
