package org.example.bogo.global.error.exception;

import lombok.Getter;


@Getter
public class BogoException extends RuntimeException {
    private final ErrorCode errorCode;

    public BogoException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public BogoException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }
}