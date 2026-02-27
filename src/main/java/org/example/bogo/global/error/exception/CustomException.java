package org.example.bogo.global.error.exception;

import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {
    ErrorCode errorCode;
    String message;

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.message=message;
        this.errorCode = errorCode;
    }
    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}