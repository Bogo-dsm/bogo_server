package org.example.bogo.global.error.exception;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {

    // 400
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "GLB_400", "잘못된 요청입니다."),

    // 401/403
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "GLB_401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "GLB_403", "권한이 없습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "GLB_401", "신용정보가 정확하지 않습니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, "GLB_401", "이메일이 인증되지 않았습니다."),

    // 404
    NOT_FOUND(HttpStatus.NOT_FOUND, "GLB_404", "리소스를 찾을 수 없습니다."),
    PART_NOT_FOUND(HttpStatus.NOT_FOUND, "GLB_404", "부품을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "GLB_404", "해당 유저가 존재하지 않습니다."),

    // 409
    CONFLICT(HttpStatus.CONFLICT, "GLB_409", "요청이 충돌했습니다."),
    PROJECT_NAME_DUPLICATE(HttpStatus.CONFLICT, "GLB_409", "이미 존재하는 프로젝트 이름입니다."),
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "GLB_409", "이미 존재하는 이메일 입니다."),


    // 422
    INVALID_VERIFICATION_CODE(HttpStatus.UNPROCESSABLE_ENTITY, "GLB_422", "이메일 인증코드가 일치하지 않습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLB_500", "서버 오류가 발생했습니다."),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "GLB_500", "이메일 전송에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    GlobalErrorCode(HttpStatus status, String code, String message) {
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
