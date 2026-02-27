package org.example.bogo.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 템플릿이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
