package com.example.tyfserver.payment.dto;

import com.example.tyfserver.payment.domain.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundInfoResponse {

    private Long amount;
    private String itemName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy'/'MM'/'dd'/' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public RefundInfoResponse(Long amount, LocalDateTime createdAt, String itemName) {
        this.amount = amount;
        this.createdAt = createdAt;
        this.itemName = itemName;
    }

    public RefundInfoResponse(Payment payment) {
        this(payment.getAmount(), payment.getCreatedAt(), payment.getItemName());
    }
}
