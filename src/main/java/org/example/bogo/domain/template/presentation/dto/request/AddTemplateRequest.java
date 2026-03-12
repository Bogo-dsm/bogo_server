package org.example.bogo.domain.template.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record AddTemplateRequest(
        @NotBlank(message = "템플릿 이름은 필수 입니다.")
        @Size(min = 1, max = 20, message = "이름은 1~20자 입니다.")
        String name,

        @Size(max = 2000, message = "설명은 2000자 이하입니다.")
        @NotBlank(message = "템플릿 설명은 필수 입니다.")
        String description,

        @NotBlank(message = "템플릿 요약은 필수 입니다.")
        @Size(min=8, max = 50, message = "요약은 50자 이하입니다.")
        String tone,

        @NotBlank(message = "AI 전용 요약은 필수 입니다.")
        String character
) {
}
