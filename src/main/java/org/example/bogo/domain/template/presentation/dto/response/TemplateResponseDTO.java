package org.example.bogo.domain.template.presentation.dto.response;

import org.example.bogo.domain.template.entity.Template;

public record TemplateResponseDTO(String name, String description) {
    public static TemplateResponseDTO from(Template template) {
        return new TemplateResponseDTO(
                template.getName(),
                template.getDescription()
        );
    }
}
