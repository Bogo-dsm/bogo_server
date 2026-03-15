package org.example.bogo.domain.report.repository;

import org.example.bogo.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findFirstByProjectMemberId(Long memberId);
}
