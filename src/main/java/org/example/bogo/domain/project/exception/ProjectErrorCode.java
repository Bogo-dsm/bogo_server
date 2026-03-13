package org.example.bogo.domain.project.exception;

import org.example.bogo.global.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProjectErrorCode implements ErrorCode {

    //400
    DUPLICATE_QUESTION_ID(HttpStatus.BAD_REQUEST,"TEM_400", "question id는 중복될 수 없습니다."),

    // 403
    NOT_AN_ADMIN(HttpStatus.FORBIDDEN, "ADMIN_403", "관리자 권한이 없습니다."),
    NOT_OWNER(HttpStatus.FORBIDDEN, "TEM_403", "본인의 작업물이 아닙니다."),

    // 404
    PROJECT_NOTFOUND(HttpStatus.NOT_FOUND, "TEM_404", "프로젝트가 존재하지 않습니다."),
    CONSTRAINT_NOTFOUND(HttpStatus.NOT_FOUND, "COT_404", "템플릿 제약조건이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ProjectErrorCode(HttpStatus status, String code, String message) {
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
