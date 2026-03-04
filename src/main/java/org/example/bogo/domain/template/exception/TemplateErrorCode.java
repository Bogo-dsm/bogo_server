package org.example.bogo.domain.template.exception;

import org.example.bogo.global.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class TemplateErrorCode implements ErrorCode {


    private final HttpStatus status;
    private final String code;
    private final String message;

    TemplateErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
