package org.example.bogo.domain.template.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final SearchRepository searchRepository;
    private final ConstraintRepository constraintRepository;

    @Transactional
    public APIResponse<?> create(AddTemplateRequest data) {
        // 1. 공통 제약조건 조회
        Constraint sharedConstraint = constraintRepository.findById(1L)
                .orElseThrow(() -> new BogoException(TemplateErrorCode.CONSTRAINT_NOTFOUND));

        // 2. DB 저장
        Template newTemplate = Template.builder()
                .name(data.name())
                .description(data.description())
                .tone(data.tone())
                .character(data.character())
                .constraint(sharedConstraint)
                .build();

        Template savedTemplate = templateRepository.save(newTemplate);

        try {
            SearchTemplate searchTemplate = SearchTemplate.builder()
                    .id(savedTemplate.getId())
                    .title(savedTemplate.getName()) // Template.name -> SearchTemplate.title 매핑
                    .description(savedTemplate.getDescription())
                    .createdAt(savedTemplate.getCreatedAt())
                    .build();

            searchRepository.save(searchTemplate);
        } catch (Exception e) {
            log.error("Failed to index template in Elasticsearch: {}", savedTemplate.getId(), e);
        }

        return new APIResponse<>(
                "OK",
                "Template created successfully.",
                data.name()
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