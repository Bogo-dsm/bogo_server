package org.example.bogo.domain.project.presentation.dto;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.project.presentation.dto.request.PostAnswerRequest;
import org.example.bogo.domain.project.presentation.dto.request.PostProjectRequest;
import org.example.bogo.domain.project.presentation.dto.response.GetQuestionResponse;
import org.example.bogo.domain.project.service.ProjectService;
import org.example.bogo.global.APIResponse;
import org.example.bogo.global.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<APIResponse<?>> create(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody PostProjectRequest request) {
        return ResponseEntity.ok()
                .body(projectService.create(user, request));
    }

    @GetMapping("/questions")
    public ResponseEntity<APIResponse<List<GetQuestionResponse>>> getQuestions() {
        return ResponseEntity.ok()
                .body(projectService.getQuestion());
    }

    @PostMapping("/answers")
    public ResponseEntity<APIResponse<?>> answers(@RequestBody PostAnswerRequest request) {
        return ResponseEntity.ok()
                .body(projectService.postAnswers(request));
    }




}
