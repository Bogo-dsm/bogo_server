package org.example.bogo.domain.template.service;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.template.entity.Constraint;
import org.example.bogo.domain.template.entity.SearchTemplate;
import org.example.bogo.domain.template.entity.Template;
import org.example.bogo.domain.template.exception.TemplateErrorCode;
import org.example.bogo.domain.template.presentation.dto.request.AddTemplateRequest;
import org.example.bogo.domain.template.presentation.dto.response.SearchDataResponse;
import org.example.bogo.domain.template.repository.ConstraintRepository;
import org.example.bogo.domain.template.repository.SearchRepository;
import org.example.bogo.domain.template.repository.TemplateRepository;
import org.example.bogo.global.APIResponse;
import org.example.bogo.global.error.exception.BogoException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final SearchRepository searchRepository;
    private final ConstraintRepository constraintRepository;

    public APIResponse<?> create(AddTemplateRequest data) {
        Constraint sharedConstraint = constraintRepository.findById(1L)
                .orElseThrow(() -> new BogoException(TemplateErrorCode.CONSTRAINT_NOTFOUND));


        Template newTemplate = Template.builder()
                .name(data.name())
                .description(data.description())
                .tone(data.tone())
                .character(data.character())
                .constraint(sharedConstraint)
                .build();

        templateRepository.save(newTemplate);

        return new APIResponse<>(
                "OK",
                "Template created successfully."
                ,data.name()
        );
    }

    public APIResponse<List<SearchDataResponse>> search(String keyword) {
        List<SearchTemplate> templateList = searchRepository.findByTitle(keyword);

        List<SearchDataResponse> response = templateList.stream()
                .map(template -> new SearchDataResponse(
                        template.getId(),
                        template.getTitle(),
                        template.getCreatedAt(),
                        template.getDescription()
                ))
                .toList();

        return new APIResponse<>(
                "OK",
                "Successfully searched templates.",
                response
        );
    }


    public APIResponse<List<SearchDataResponse>> all() {
        List<Template> templateList = templateRepository.findAll();
        List<SearchDataResponse> response = templateList.stream()
                .map(template -> new SearchDataResponse(
                        template.getId(),
                        template.getName(),
                        template.getCreatedAt(),
                        template.getDescription()
                ))
                .toList();
        return new APIResponse<>(
                "OK",
                "Successfully get all templates"
                ,response
        );
    }

    public APIResponse<?> getCharacter(Long id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new BogoException(TemplateErrorCode.TEMPLATE_NOTFOUND));
        return new APIResponse<>(
                "OK",
                "Template features get successfully."
                ,template.getCharacter()
        );
    }
}