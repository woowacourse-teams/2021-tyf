package com.example.tyfserver.common.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String message;

    public ErrorResponse(final String message) {
        this.message = message;
    }
}
