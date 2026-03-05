package org.example.bogo.global.error.exception;

import lombok.Getter;


@Getter
public class BogoException extends RuntimeException {
    ErrorCode errorCode;
    String message;

    public BogoException(ErrorCode errorCode, String message) {
        super(message);
        this.message=message;
        this.errorCode = errorCode;
    }
    public BogoException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}