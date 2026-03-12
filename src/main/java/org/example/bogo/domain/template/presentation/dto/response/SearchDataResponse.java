package org.example.bogo.domain.template.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record SearchDataResponse(
        @JsonProperty("template_id")
        Long templateId,
        @JsonProperty("template_name")
        String templateName,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("description")
        String description
) {
}