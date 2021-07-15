package com.example.tyfserver.common.config;

import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.common.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handle(BaseException exception) {
        return ResponseEntity.badRequest().body(exception.toResponse());
    }
}
