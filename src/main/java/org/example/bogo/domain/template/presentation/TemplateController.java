package org.example.bogo.domain.template.presentation;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.template.presentation.dto.response.TemplateResponseDTO;
import org.example.bogo.domain.template.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template")
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/{template_id}")
    public ResponseEntity<TemplateResponseDTO> getTemplate(@PathVariable(name="template_id") Long template_id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(templateService.getTemplate(template_id));
    }
}
