package org.example.bogo.domain.report.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record GetProjectSummaryResponse(
        @JsonProperty("project_id")
        Long projectId,

        @JsonProperty("template_id")
        Long templateId,

        String title,

        String content,

        LocalDateTime createdAt
) {
}
