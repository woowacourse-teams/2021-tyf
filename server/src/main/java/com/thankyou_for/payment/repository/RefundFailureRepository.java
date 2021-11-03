package com.thankyou_for.payment.repository;

import com.thankyou_for.payment.domain.RefundFailure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundFailureRepository extends JpaRepository<RefundFailure, Long> {
}
