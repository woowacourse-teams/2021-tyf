package com.example.tyfserver.payment.repository;

import com.example.tyfserver.payment.domain.RefundFailure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundFailureRepository extends JpaRepository<RefundFailure, Long> {
}
