package com.example.tyfserver.payment.repository;

import com.example.tyfserver.payment.domain.Payment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @EntityGraph(attributePaths = {"member"})
    Optional<Payment> findByMerchantUid(UUID merchantUid);

    @EntityGraph(attributePaths = {"refundFailure"})
    @Query("select p from Payment p where p.merchantUid =:merchantUid")
    Optional<Payment> findByMerchantUidWithRefundFailure(@Param("merchantUid") UUID merchantUid);

}
