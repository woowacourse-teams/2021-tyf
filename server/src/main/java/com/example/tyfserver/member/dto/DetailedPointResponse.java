package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailedPointResponse {

    private Long currentPoint;
    private Long exchangedTotalPoint;

    public DetailedPointResponse(Long currentPoint, Long exchangedTotalPoint) {
        this.currentPoint = currentPoint;
        this.exchangedTotalPoint = exchangedTotalPoint;
    }
}
