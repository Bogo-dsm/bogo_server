package org.example.bogo.global.error.handler;

import org.example.bogo.global.error.exception.BogoException;
import org.example.bogo.global.error.exception.ErrorCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("검증에 실패하였습니다.");

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("code", "VALIDATION_ERROR");
        body.put("message", errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

}