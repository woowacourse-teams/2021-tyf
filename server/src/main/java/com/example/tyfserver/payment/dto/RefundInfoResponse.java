package com.example.tyfserver.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundInfoResponse {

    private Long point;
    private Long price;
    private String itemName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy'/'MM'/'dd'/' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public RefundInfoResponse(Long point, Long price, LocalDateTime createdAt, String itemName) {
        this.point = point;
        this.price = price;
        this.createdAt = createdAt;
        this.itemName = itemName;
    }
}
