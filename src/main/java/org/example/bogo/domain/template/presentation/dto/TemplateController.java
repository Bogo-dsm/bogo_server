package org.example.bogo.domain.template.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.template.presentation.dto.request.AddTemplateRequest;
import org.example.bogo.domain.template.presentation.dto.response.SearchDataResponse;
import org.example.bogo.domain.template.service.TemplateService;
import org.example.bogo.global.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    // api key 로 검증 (AI to BE)
    @PostMapping("/create")
    public ResponseEntity<APIResponse<?>> create(@Valid @RequestBody AddTemplateRequest data) {
        return ResponseEntity.ok()
                .body(templateService.create(data));
    }

    // 검색 (elasticsearch 사용)
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<SearchDataResponse>>> search(@RequestParam @NotBlank(message = "검색어는 비어있을 수 없습니다.") String keyword) {
        return ResponseEntity.ok()
                .body(templateService.search(keyword));
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<SearchDataResponse>>> searchAll() {
        return ResponseEntity.ok()
                .body(templateService.all());
    }

    // for Ai prompting
    @GetMapping("/feature/{templateId}")
    public ResponseEntity<APIResponse<?>> searchFeature(@PathVariable Long templateId) {
        return ResponseEntity.ok()
                .body(templateService.getCharacter(templateId));
    }

}
