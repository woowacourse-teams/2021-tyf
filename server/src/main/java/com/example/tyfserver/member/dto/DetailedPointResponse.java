package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailedPointResponse {

    private Long possessedPoint;
    private Long exchangeablePoint;
    private Long exchangedTotalPoint;

    public DetailedPointResponse(Long possessedPoint, Long exchangeablePoint, Long exchangedTotalPoint) {
        this.possessedPoint = possessedPoint;
        this.exchangeablePoint = exchangeablePoint;
        this.exchangedTotalPoint = exchangedTotalPoint;
    }
}
