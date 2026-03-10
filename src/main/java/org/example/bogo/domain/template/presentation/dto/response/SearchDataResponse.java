package org.example.bogo.domain.template.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record SearchDataResponse(
        @JsonProperty(namespace = "template_id")
        Long templateId,
        @JsonProperty(namespace = "template_name")
        String templateName,
        @JsonProperty(namespace = "created_at")
        LocalDateTime createdAt,
        @JsonProperty(namespace = "description")
        String description
) {
}
