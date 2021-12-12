package com.thankyou_for.common.config;

import com.thankyou_for.common.dto.ErrorResponse;
import com.thankyou_for.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handle(BaseException exception) {
        logger.info("=== EXCEPTION ===", exception);
        return ResponseEntity.badRequest().body(exception.toResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleNotDefinedException(Exception exception) {
        logger.error("=== UNDEFINED EXCEPTION ===", exception);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("error-000", "예상하지 못한 에러가 발생했습니다."));
    }
}
