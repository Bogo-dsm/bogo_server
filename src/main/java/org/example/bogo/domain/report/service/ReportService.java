package org.example.bogo.domain.report.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.report.entity.Report;
import org.example.bogo.domain.report.exception.ReportErrorCode;
import org.example.bogo.domain.report.repository.ReportRepository;
import org.example.bogo.global.APIResponse;
import org.example.bogo.global.error.exception.BogoException;
import org.example.bogo.global.security.userdetails.CustomUserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    @Transactional
    public APIResponse<String> getReport(CustomUserDetails user) {
        Report report = reportRepository.findFirstByProjectMemberId(user.getId())
                .orElseThrow(() -> new BogoException(ReportErrorCode.REPORT_NOT_FOUND));

        return new APIResponse<>(
                "OK",
                "successfully posted profile.",
                report.getPdfPath()
        );
    }
}
