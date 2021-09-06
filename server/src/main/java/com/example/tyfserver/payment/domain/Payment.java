package com.example.tyfserver.payment.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
import com.example.tyfserver.payment.exception.PaymentAlreadyCancelledException;
import com.example.tyfserver.payment.exception.RefundVerificationBlockedException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.tyfserver.payment.exception.IllegalPaymentInfoException.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Embedded
    private Email email;

    @Column(nullable = false)
    private String itemName;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String impUid;

    @Column(nullable = false)
    private UUID merchantUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_failure_id")
    private RefundFailure refundFailure;

    public Payment(Long id, Long amount, Email email, String itemName, UUID merchantUid) {
        this.id = id;
        this.amount = amount;
        this.email = email;
        this.itemName = itemName;
        this.merchantUid = merchantUid;
    }

    public Payment(Long id, Long amount, String email, String itemName, UUID merchantUid) {
        this(id, amount, new Email(email), itemName, merchantUid);
    }

    public Payment(Long amount, String email, String itemName, UUID merchantUid) {
        this(null, amount, email, itemName, merchantUid);
    }

    public Payment(Long id, Long amount, String email, String itemName) {
        this(id, amount, email, itemName, UUID.randomUUID());
    }

    public Payment(Long amount, String email, String itemName) {
        this(null, amount, email, itemName, UUID.randomUUID());
    }

    public String getMaskedEmail() {
        return email.maskedEmail();
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.status = paymentStatus;
    }

    public void updateRefundFailure(RefundFailure refundFailure) {
        this.refundFailure = refundFailure;
    }

    public void complete(PaymentInfo paymentInfo) {
        validatePaymentComplete(paymentInfo);
        this.impUid = paymentInfo.getImpUid();
        this.status = PaymentStatus.PAID;
    }

    private void validatePaymentComplete(PaymentInfo paymentInfo) {
        if (paymentInfo.getStatus() != PaymentStatus.PAID) {
            updateStatus(paymentInfo.getStatus());
            throw IllegalPaymentInfoException.from(ERROR_CODE_NOT_PAID, paymentInfo.getModule());
        }

        validatePaymentInfo(paymentInfo);
    }

    public void refund(PaymentInfo paymentInfo) {
        validatePaymentCancel(paymentInfo);
        this.impUid = paymentInfo.getImpUid();
        this.status = PaymentStatus.CANCELLED;
        member.reducePoint(amount);
    }

    private void validatePaymentCancel(PaymentInfo paymentInfo) {
        if (paymentInfo.getStatus() != PaymentStatus.CANCELLED) {
            updateStatus(paymentInfo.getStatus());
            throw IllegalPaymentInfoException.from(ERROR_CODE_NOT_CANCELLED, paymentInfo.getModule());
        }

        validatePaymentInfo(paymentInfo);
    }

    private void validatePaymentInfo(PaymentInfo paymentInfo) {
        if (!merchantUid.equals(paymentInfo.getMerchantUid())) {
            updateStatus(PaymentStatus.INVALID);
            throw IllegalPaymentInfoException.from(ERROR_CODE_INVALID_MERCHANT_ID, paymentInfo.getModule());
        }

        if (!amount.equals(paymentInfo.getAmount())) {
            updateStatus(PaymentStatus.INVALID);
            throw IllegalPaymentInfoException.from(ERROR_CODE_INVALID_AMOUNT, paymentInfo.getModule());
        }

        if (!itemName.equals(paymentInfo.getPageName())) {
            updateStatus(PaymentStatus.INVALID);
            throw IllegalPaymentInfoException.from(ERROR_CODE_INVALID_CREATOR, paymentInfo.getModule());
        }
    }

    public void checkRemainTryCount() {
        if (refundFailure != null && refundFailure.getRemainTryCount() == 0) {
            throw new RefundVerificationBlockedException();
        }
    }

    public void reduceTryCount() {
        refundFailure.reduceTryCount();
    }

    public boolean hasNoRefundFailure() {
        return refundFailure == null;
    }

    public int getRemainTryCount() {
        return refundFailure.getRemainTryCount();
    }

    public void validateIsNotCancelled() {
        if (status == PaymentStatus.CANCELLED) {
            throw new PaymentAlreadyCancelledException();
        }
    }

    public String getEmail() {
        return email.getEmail();
    }

    public boolean isRefundBlocked() {
        return refundFailure != null && refundFailure.isBlocked();
    }

    public boolean isNotPaid() {
        return status != PaymentStatus.PAID;
    }

    public boolean isAfterRefundGuaranteeDuration() {
        return LocalDateTime.now()
                .isAfter(getCreatedAt().plusDays(7));
    }

    public void validateMemberHasRefundablePoint() {
        member.validateEnoughPoint(amount);
    }
}
