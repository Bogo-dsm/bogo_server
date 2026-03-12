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
import org.example.bogo.global.error.exception.GlobalErrorCode;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final SearchRepository searchRepository;
    private final ConstraintRepository constraintRepository;
    private final ElasticsearchOperations elasticsearchOperations;

//    @Transactional
//    public APIResponse<?> create(AddTemplateRequest data) {
//        // 1. 공통 제약조건 조회
//        Constraint sharedConstraint = constraintRepository.findById(1L)
//                .orElseThrow(() -> new BogoException(TemplateErrorCode.CONSTRAINT_NOTFOUND));
//
//        // 2. DB 저장
//        Template newTemplate = Template.builder()
//                .name(data.name())
//                .description(data.description())
//                .tone(data.tone())
//                .character(data.character())
//                .constraint(sharedConstraint)
//                .build();
//
//        Template savedTemplate = templateRepository.save(newTemplate);
//
//        try {
//            SearchTemplate searchTemplate = SearchTemplate.builder()
//                    .id(savedTemplate.getId())
//                    .title(savedTemplate.getName()) // Template.name -> SearchTemplate.title 매핑
//                    .description(savedTemplate.getDescription())
//                    .createdAt(savedTemplate.getCreatedAt())
//                    .build();
//
//            searchRepository.save(searchTemplate);
//        } catch (Exception e) {
//            log.error("Failed to index template in Elasticsearch: {}", savedTemplate.getId(), e);
//        }
//
//        return new APIResponse<>(
//                "OK",
//                "Template created successfully.",
//                data.name()
//        );
//    }
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

    // saveAndFlush를 사용하여 createdAt(Auditing) 값이 즉시 생성되도록 함
    Template savedTemplate = templateRepository.saveAndFlush(newTemplate);

    // 3. Elasticsearch 저장
    SearchTemplate searchTemplate = SearchTemplate.builder()
            .id(savedTemplate.getId())
            .title(savedTemplate.getName())
            .description(savedTemplate.getDescription())
            .createdAt(savedTemplate.getCreatedAt()) // 이제 null이 아님
            .build();

    try {
        searchRepository.save(searchTemplate);
        // create 직후 search API에서도 바로 조회되도록 인덱스 refresh를 강제한다.
        elasticsearchOperations.indexOps(SearchTemplate.class).refresh();
    } catch (Exception e) {
        log.error("Elasticsearch indexing failed for ID: {}", savedTemplate.getId(), e);
        // 비즈니스 요구사항에 따라 throw를 던져 DB 저장까지 롤백할지 결정 필요
        throw new RuntimeException("Search indexing failed");
    }

    return new APIResponse<>("OK", "Template created successfully.", data.name());
}



    public APIResponse<List<SearchDataResponse>> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            throw new BogoException(GlobalErrorCode.INVALID_REQUEST);
        }

        String normalizedKeyword = keyword.trim();
        List<SearchDataResponse> response;

        try {
            String wildcardKeyword = "*" + escapeWildcardKeyword(normalizedKeyword) + "*";

            NativeQuery searchQuery = NativeQuery.builder()
                    .withQuery(q -> q.bool(b -> b
                            .should(s -> s.match(m -> m
                                    .field("title")
                                    .query(normalizedKeyword)))
                            .should(s -> s.matchPhrasePrefix(m -> m
                                    .field("title")
                                    .query(normalizedKeyword)))
                            .should(s -> s.wildcard(w -> w
                                    .field("title")
                                    .value(wildcardKeyword)
                                    .caseInsensitive(true)))
                            .should(s -> s.match(m -> m
                                    .field("description")
                                    .query(normalizedKeyword)))
                            .minimumShouldMatch("1")
                    ))
                    .withMaxResults(50)
                    .build();

            SearchHits<SearchTemplate> hits = elasticsearchOperations.search(searchQuery, SearchTemplate.class);
            List<SearchTemplate> templateList = hits.getSearchHits().stream()
                    .map(SearchHit::getContent)
                    .toList();

            response = templateList.stream()
                    .map(template -> new SearchDataResponse(
                            template.getId(),
                            template.getTitle(),
                            template.getCreatedAt(),
                            template.getDescription()
                    ))
                    .toList();
        } catch (Exception e) {
            log.error("Elasticsearch search failed. keyword={}", normalizedKeyword, e);
            throw new BogoException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }

        return new APIResponse<>(
                "OK",
                "Successfully searched templates.",
                response
        );
    }

    private String escapeWildcardKeyword(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("*", "\\*")
                .replace("?", "\\?");
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