package org.example.bogo.domain.report.exception;

import org.example.bogo.global.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ReportErrorCode implements ErrorCode {

    // 403
    NOT_OWNER(HttpStatus.FORBIDDEN, "REP_403", "본인 소유의 프로젝트 id가 아닙니다."),

    // 404
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REP_404", "보고서가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ReportErrorCode(HttpStatus status, String code, String message) {
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
