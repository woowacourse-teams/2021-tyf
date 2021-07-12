package com.example.tyfserver.member.dto;

import lombok.Getter;

@Getter
public class PointResponse {

    private long point;

    public PointResponse(long point) {
        this.point = point;
    }
}
