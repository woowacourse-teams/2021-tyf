package com.example.tyfserver.common.config;

import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.common.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> errorHandling(BaseException exception) {
        return ResponseEntity.badRequest().body(exception.toResponse());
    }
}
