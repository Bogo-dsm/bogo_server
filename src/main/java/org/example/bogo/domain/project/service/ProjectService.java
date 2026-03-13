package org.example.bogo.domain.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.member.entity.Member;
import org.example.bogo.domain.member.repository.MemberRepository;
import org.example.bogo.domain.project.entity.Project;
import org.example.bogo.domain.project.exception.ProjectErrorCode;
import org.example.bogo.domain.project.presentation.dto.request.PostAnswerRequest;
import org.example.bogo.domain.project.presentation.dto.request.PostProjectRequest;
import org.example.bogo.domain.project.presentation.dto.response.GetQuestionResponse;
import org.example.bogo.domain.project.presentation.dto.response.PostProjectResponse;
import org.example.bogo.domain.project.repository.ProjectRepository;
import org.example.bogo.domain.template.entity.Constraint;
import org.example.bogo.domain.template.entity.Template;
import org.example.bogo.domain.template.exception.TemplateErrorCode;
import org.example.bogo.domain.template.repository.ConstraintRepository;
import org.example.bogo.domain.template.repository.TemplateRepository;
import org.example.bogo.global.APIResponse;
import org.example.bogo.global.error.exception.BogoException;
import org.example.bogo.global.error.exception.GlobalErrorCode;
import org.example.bogo.global.security.userdetails.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final TemplateRepository templateRepository;
    private final ConstraintRepository constraintRepository;

    @Transactional
    public APIResponse<PostProjectResponse> create(CustomUserDetails user, PostProjectRequest data) {

        Member member = memberRepository.findById(user.getId())
                .orElseThrow(() -> new BogoException(GlobalErrorCode.MEMBER_NOT_FOUND));

        Template template = templateRepository.findById(data.templateId())
                .orElseThrow(() -> new BogoException(TemplateErrorCode.TEMPLATE_NOTFOUND));

        Project newProject = Project.builder()
                .title(data.projectName())
                .description(data.description())
                .member(member)
                .template(template)
                .build();

        Project savedProject = projectRepository.save(newProject);

        PostProjectResponse response = new PostProjectResponse(
                savedProject.getId(),
                savedProject.getTitle()
        );

        return new APIResponse<>(
                "CREATED",
                "Project created successfully."
                ,response
        );
    }

    @Transactional
    public APIResponse<List<GetQuestionResponse>> getQuestion() {
        // 1. List<Constraint>가 아니라 단일 객체 'Constraint'로 가져와야 합니다.
        Constraint constraint = constraintRepository.findById(1L)
                .orElseThrow(()-> new BogoException(ProjectErrorCode.CONSTRAINT_NOTFOUND));

        // 2. 이제 constraint.getQuestions() 호출이 가능합니다.
        List<GetQuestionResponse> questions = constraint.getQuestions().stream()
                .map(q -> {
                    // Object를 안전하게 String으로 바꾼 뒤 변환
                    Long qId = Long.valueOf(String.valueOf(q.get("qId")));
                    String question = String.valueOf(q.get("question"));

                    return new GetQuestionResponse(qId, question);
                })
                .sorted(Comparator.comparing(GetQuestionResponse::qId))
                .toList();

        return new APIResponse<>(
                "OK",
                "successfully get questions.",
                questions
        );
    }

    @Transactional
    public APIResponse<?> postAnswers(PostAnswerRequest data) {
        Project project = projectRepository.findById(data.projectId())
                .orElseThrow(() -> new BogoException(ProjectErrorCode.PROJECT_NOTFOUND));

        Long currentMemberId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!project.getMember().getId().equals(currentMemberId)) {
            throw new BogoException(ProjectErrorCode.NOT_OWNER); // 또는 FORBIDDEN 관련 코드
        }

        long distinctCount = data.answers().stream()
                .map(PostAnswerRequest.Answer::qId)
                .distinct()
                .count();

        if (distinctCount != data.answers().size()) {
            throw new BogoException(ProjectErrorCode.DUPLICATE_QUESTION_ID);
        }

        // 2. Map 변환 (중복 해결 및 null 처리 추가)
        Map<String, Object> answerMap = data.answers().stream()
                .collect(Collectors.toMap(
                        answer -> String.valueOf(answer.qId()), // Long -> String 변환
                        answer -> answer.a() == null ? "" : answer.a(), // null 방어 로직
                        (existing, replacement) -> replacement // 중복 키 발생 시 마지막 값 취함
                ));
        project.updateAnswers(answerMap);
        projectRepository.save(project);

        return new APIResponse<>(
                "OK",
                "successfully posted answers.",
                answerMap
        );

    }

}