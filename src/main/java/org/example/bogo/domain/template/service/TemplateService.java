package org.example.bogo.domain.template.service;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.template.exception.TemplateNotFoundException;
import org.example.bogo.domain.template.presentation.dto.response.TemplateResponseDTO;
import org.example.bogo.domain.template.repository.TemplateRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateResponseDTO getTemplate(Long id) {

        var template = templateRepository.findById(id)
                .orElseThrow(TemplateNotFoundException::new);
        return TemplateResponseDTO.from(template);
    }
}
