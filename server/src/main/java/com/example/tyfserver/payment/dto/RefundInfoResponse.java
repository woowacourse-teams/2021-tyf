package com.example.tyfserver.payment.dto;

import com.example.tyfserver.payment.domain.Email;
import com.example.tyfserver.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundInfoResponse {

    private UUID merchantUid;
    private Long amount;
    private LocalDateTime createdAt;
    private Email email;
    private String pageName;

    public RefundInfoResponse(Payment payment) {
        merchantUid = payment.getMerchantUid();
        amount = payment.getAmount();
        createdAt = payment.getCreatedAt();
        email = payment.getEmail();
        pageName = payment.getPageName();
    }
}
