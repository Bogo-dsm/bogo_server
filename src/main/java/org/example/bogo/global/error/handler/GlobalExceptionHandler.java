package org.example.bogo.global.error.handler;

import org.example.bogo.global.error.exception.BogoException;
import org.example.bogo.global.error.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BogoException.class)
    public ResponseEntity<?> handleBogoException(BogoException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(Map.of(
                        "status", errorCode.getStatus().value(),
                        "code", errorCode.getCode(),
                        "message", errorCode.getMessage()
                ));
    }

}
