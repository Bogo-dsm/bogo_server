package org.example.bogo.domain.template.service;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.member.repository.MemberRepository;
import org.example.bogo.domain.template.entity.SearchTemplate;
import org.example.bogo.domain.template.entity.Template;
import org.example.bogo.domain.template.exception.TemplateErrorCode;
import org.example.bogo.domain.template.presentation.dto.request.AddTemplateRequest;
import org.example.bogo.domain.template.presentation.dto.response.SearchDataResponse;
import org.example.bogo.domain.template.repository.SearchRepository;
import org.example.bogo.domain.template.repository.TemplateRepository;
import org.example.bogo.global.APIResponse;
import org.example.bogo.global.error.exception.BogoException;
import org.example.bogo.global.security.userdetails.CustomUserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final SearchRepository searchRepository;

    public APIResponse<?> create(AddTemplateRequest data) {

        Template newTemplate = Template.builder()
                .name(data.name())
                .description(data.description())
                .tone(data.tone())
                .character(data.character())
                .build();

        templateRepository.save(newTemplate);

        return new APIResponse<>(
                "OK",
                "Template created successfully."
                ,data.name()
        );
    }

    public APIResponse<List<SearchDataResponse>> search(String keyword) {

        List<SearchTemplate> templateList = searchRepository.findByTitle(keyword)
                .orElseThrow(() -> new BogoException(TemplateErrorCode.TEMPLATE_NOTFOUND));

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