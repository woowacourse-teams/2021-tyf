package com.example.tyfserver.payment.repository;

import com.example.tyfserver.common.config.JpaAuditingConfig;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.domain.RefundFailure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class PaymentRepositoryTest {

    private static final UUID MERCHANT_UID = UUID.randomUUID();
    private static final long AMOUNT = 1000L;
    private static final String PAGE_NAME = "test";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RefundFailureRepository refundFailureRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public static Payment testPayment() {
        return new Payment(AMOUNT, "test@test.com", PAGE_NAME, MERCHANT_UID);
    }

    @DisplayName("환불 실패 정보를 업데이트한다")
    @Test
    void testUpdateRefundFailure() {
        //given
        Payment payment = testPayment();
        paymentRepository.save(payment);
        RefundFailure refundFailure = refundFailureRepository.save(new RefundFailure());

        //when
        payment.updateRefundFailure(refundFailure);

        entityManager.flush();
        entityManager.clear();

        //then
        Payment findPayment = paymentRepository.findByMerchantUid(payment.getMerchantUid()).get();
        assertThat(findPayment.getRefundFailure()).isNotNull();
        assertThat(findPayment.getRefundFailure().getRemainTryCount()).isEqualTo(10);
    }

}