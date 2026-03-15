package org.example.bogo.domain.report.presentation.dto;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.report.service.ReportService;
import org.example.bogo.global.APIResponse;
import org.example.bogo.global.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/results")
    public ResponseEntity<APIResponse<String>> getReport(
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok()
                .body(reportService.getReport(user));
    }
}
