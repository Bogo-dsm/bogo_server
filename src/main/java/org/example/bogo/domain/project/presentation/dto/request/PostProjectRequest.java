package org.example.bogo.domain.project.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostProjectRequest(
        @JsonProperty("template_id")
        @NotNull(message = "템플릿 id는 필수 입니다.")
        Long templateId,
        @JsonProperty("project_name")
        @NotBlank(message = "프로젝트 이름은 필수입니다.")
        @Size(min = 1,max = 20,message = "프로젝트 이름은 1~20자 입니다.")
        String projectName,
        @Size(min = 1, max = 150, message = "프로젝트 설명은 150자 이하 입니다.")
        String description
) {
}