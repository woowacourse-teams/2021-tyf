package com.example.tyfserver.payment.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.payment.exception.PaymentCancelException;
import com.example.tyfserver.payment.exception.PaymentRequestException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.UUID;

import static com.example.tyfserver.payment.exception.PaymentRequestException.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false)
    private Long amount;

    private String email;

    @Column(nullable = false)
    private String pageName;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String impUid;

    public Payment(UUID id, Long amount, String email, String pageName) {
        this.id = id;
        this.amount = amount;
        this.email = email;
        this.pageName = pageName;
    }

    public Payment(Long amount, String email, String pageName) {
        this(null, amount, email, pageName);
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.status = paymentStatus;
    }

    public void complete(PaymentInfo paymentInfo) {
        validatePaymentComplete(paymentInfo);
        this.impUid = paymentInfo.getImpUid();
        this.status = PaymentStatus.PAID;
    }

    private void validatePaymentComplete(PaymentInfo paymentInfo) {
        if (!PaymentStatus.isPaid(paymentInfo.getStatus())) {
            updateStatus(paymentInfo.getStatus());
            throw new PaymentRequestException(ERROR_CODE_NOT_PAID);
        }

        validatePaymentInfo(paymentInfo);
    }

    public void cancel(PaymentInfo paymentInfo) {
        validatePaymentCancel(paymentInfo);
        this.impUid = paymentInfo.getImpUid();
        this.status = PaymentStatus.CANCELLED;
    }

    private void validatePaymentCancel(PaymentInfo paymentInfo) {
        if (!PaymentStatus.isCancelled(paymentInfo.getStatus())) {
            updateStatus(paymentInfo.getStatus());
            throw new PaymentCancelException();
        }

        validatePaymentInfo(paymentInfo);
    }

    private void validatePaymentInfo(PaymentInfo paymentInfo) {
        if (!id.equals(paymentInfo.getMerchantUid())) {
            updateStatus(PaymentStatus.INVALID);
            throw new PaymentRequestException(ERROR_CODE_INVALID_MERCHANT_ID);
        }

        if (!amount.equals(paymentInfo.getAmount())) {
            updateStatus(PaymentStatus.INVALID);
            throw new PaymentRequestException(ERROR_CODE_INVALID_AMOUNT);
        }

        if (!pageName.equals(paymentInfo.getPageName())) {
            updateStatus(PaymentStatus.INVALID);
            throw new PaymentRequestException(ERROR_INVALID_CREATOR);
        }
    }
}
