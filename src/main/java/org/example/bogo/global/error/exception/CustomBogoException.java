package org.example.bogo.global.error.exception;

import lombok.Getter;


@Getter
public class CustomBogoException extends RuntimeException {
    ErrorCode errorCode;
    String message;

    public CustomBogoException(ErrorCode errorCode, String message) {
        super(message);
        this.message=message;
        this.errorCode = errorCode;
    }
    public CustomBogoException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}