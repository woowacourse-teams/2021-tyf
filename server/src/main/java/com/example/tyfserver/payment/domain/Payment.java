package com.example.tyfserver.payment.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.donation.exception.DonationAlreadyCancelledException;
import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
import com.example.tyfserver.payment.exception.PaymentAlreadyCancelledException;
import com.example.tyfserver.payment.exception.RefundVerificationBlockedException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String pageName;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String impUid;

    @Column(nullable = false)
    private UUID merchantUid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_failure_id")
    private RefundFailure refundFailure;

    public Payment(Long id, Long amount, Email email, String pageName, UUID merchantUid) {
        this.id = id;
        this.amount = amount;
        this.email = email;
        this.pageName = pageName;
        this.merchantUid = merchantUid;
    }

    public Payment(Long id, Long amount, String email, String pageName, UUID merchantUid) {
        this(id, amount, new Email(email), pageName, merchantUid);
    }

    public Payment(Long amount, String email, String pageName, UUID merchantUid) {
        this(null, amount, email, pageName, merchantUid);
    }

    public Payment(Long id, Long amount, String email, String pageName) {
        this(id, amount, email, pageName, UUID.randomUUID());
    }

    public Payment(Long amount, String email, String pageName) {
        this(null, amount, email, pageName, UUID.randomUUID());
    }

    public String getMaskedEmail() {
        return email.maskedEmail();
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.status = paymentStatus;
    }

    public void complete(PaymentInfo paymentInfo) {
        validatePaymentComplete(paymentInfo);
        this.impUid = paymentInfo.getImpUid();
        this.status = PaymentStatus.PAID;
    }

    public void updateRefundFailure(RefundFailure refundFailure) {
        this.refundFailure = refundFailure;
    }

    private void validatePaymentComplete(PaymentInfo paymentInfo) {
        if (!PaymentStatus.isPaid(paymentInfo.getStatus())) {
            updateStatus(paymentInfo.getStatus());
            throw IllegalPaymentInfoException.from(ERROR_CODE_NOT_PAID, paymentInfo.getModule());
        }

        validatePaymentInfo(paymentInfo);
    }

    public void refund(PaymentInfo paymentInfo) {
        validatePaymentCancel(paymentInfo);
        this.impUid = paymentInfo.getImpUid();
        this.status = PaymentStatus.CANCELLED;
    }

    private void validatePaymentCancel(PaymentInfo paymentInfo) {
        if (!PaymentStatus.isCancelled(paymentInfo.getStatus())) {
            updateStatus(paymentInfo.getStatus());
            // todo: 예외 3: 아임포트에서 조회한 결제 정보와 우리 서버에 저장된 정보가 일치하지 않은 경우 예외! 모킹
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

        if (!pageName.equals(paymentInfo.getPageName())) {
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
}
