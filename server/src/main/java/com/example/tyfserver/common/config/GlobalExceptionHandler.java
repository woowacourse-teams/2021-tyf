package com.example.tyfserver.common.config;

import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handle(BaseException exception) {
        log.error("===ERROR===", exception);
        return ResponseEntity.badRequest().body(exception.toResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleNotDefinedException(Exception exception) {
        log.error("===ERROR_UNDEFINE===", exception);
        return ResponseEntity.badRequest().body(new ErrorResponse("error-000", "예상하지 못한 에러가 발생했습니다."));
    }
}
