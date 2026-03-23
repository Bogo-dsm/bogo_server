package org.example.bogo.domain.report.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.project.entity.Project;
import org.example.bogo.domain.project.repository.ProjectRepository;
import org.example.bogo.domain.report.entity.Report;
import org.example.bogo.domain.report.exception.ReportErrorCode;
import org.example.bogo.domain.report.presentation.dto.response.GetPdfPathResponse;
import org.example.bogo.domain.report.presentation.dto.response.GetProjectDetailResponse;
import org.example.bogo.domain.report.presentation.dto.response.GetProjectSummaryResponse;
import org.example.bogo.domain.report.repository.ReportRepository;
import org.example.bogo.global.APIResponse;
import org.example.bogo.global.error.exception.BogoException;
import org.example.bogo.global.error.exception.GlobalErrorCode;
import org.example.bogo.global.security.userdetails.CustomUserDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public APIResponse<List<GetProjectSummaryResponse>> getProjectList(CustomUserDetails user, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());

        List<GetProjectSummaryResponse> response = reportRepository.findAllByProjectMemberId(user.getId(), pageable)
                .stream()
                .map(r -> new GetProjectSummaryResponse(
                        r.getProject().getId(),
                        r.getProject().getTemplate().getId(),
                        r.getProject().getTitle(),
                        r.getContent(),
                        r.getProject().getCreatedAt()
                ))
                .toList();

        if (response.isEmpty()) {
            throw new BogoException(GlobalErrorCode.MEMBER_NOT_FOUND);
        }

        return new APIResponse<>(
                "OK",
                "프로젝트 정보를 성공적으로 조회했습니다.",
                response
        );
    }

    @Transactional
    public APIResponse<List<GetProjectDetailResponse>> getProjectDetail(CustomUserDetails user, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BogoException(GlobalErrorCode.MEMBER_NOT_FOUND));

        if (!project.getMember().getId().equals(user.getId())) {
            throw new BogoException(ReportErrorCode.NOT_OWNER);
        }

        if (project.getAnswers() == null || project.getAnswers().isEmpty()) {
            throw new BogoException(ReportErrorCode.REPORT_NOT_FOUND);
        }

        List<GetProjectDetailResponse> response = project.getAnswers().entrySet().stream()
                .map(entry -> new GetProjectDetailResponse(
                        Long.valueOf(entry.getKey()),
                        String.valueOf(entry.getValue())
                ))
                .sorted((a, b) -> Long.compare(a.qId(), b.qId()))
                .toList();

        return new APIResponse<>(
                "OK",
                "프로젝트 상세 정보를 성공적으로 조회했습니다.",
                response
        );
    }

    @Transactional
    public APIResponse<GetPdfPathResponse> getResult(CustomUserDetails user, Long reportId) {
        Report report = reportRepository.findByProjectId(reportId)
                .orElseThrow(() -> new BogoException(ReportErrorCode.REPORT_NOT_FOUND));

        if (!report.getProject().getMember().getId().equals(user.getId())) {
            throw new BogoException(ReportErrorCode.NOT_OWNER);
        }

        return new APIResponse<>(
                "OK",
                "PDF 경로를 성공적으로 조회했습니다.",
                new GetPdfPathResponse(report.getPdfPath())
        );
    }
}
