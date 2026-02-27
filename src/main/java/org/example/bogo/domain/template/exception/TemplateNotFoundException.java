package org.example.bogo.domain.template.exception;

import org.example.bogo.global.error.exception.CustomException;
import org.example.bogo.global.error.exception.ErrorCode;

public class TemplateNotFoundException extends CustomException {
    public TemplateNotFoundException() {
        super(ErrorCode.TEMPLATE_NOT_FOUND, ErrorCode.TEMPLATE_NOT_FOUND.getMessage());
    }
}
