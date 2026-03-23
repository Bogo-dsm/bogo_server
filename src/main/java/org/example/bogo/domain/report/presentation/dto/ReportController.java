package org.example.bogo.domain.report.presentation.dto;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.report.presentation.dto.response.GetPdfPathResponse;
import org.example.bogo.domain.report.presentation.dto.response.GetProjectDetailResponse;
import org.example.bogo.domain.report.presentation.dto.response.GetProjectSummaryResponse;
import org.example.bogo.domain.report.service.ReportService;
import org.example.bogo.global.APIResponse;
import org.example.bogo.global.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/library")
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<APIResponse<List<GetProjectSummaryResponse>>> getProjectList(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok()
                .body(reportService.getProjectList(user, page));
    }

    @GetMapping("/detail/{projectId}")
    public ResponseEntity<APIResponse<List<GetProjectDetailResponse>>> getProjectDetail(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long projectId) {
        return ResponseEntity.ok()
                .body(reportService.getProjectDetail(user, projectId));
    }

    @GetMapping("/result/{reportId}")
    public ResponseEntity<APIResponse<GetPdfPathResponse>> getResult(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long reportId) {
        return ResponseEntity.ok()
                .body(reportService.getResult(user, reportId));
    }
}
