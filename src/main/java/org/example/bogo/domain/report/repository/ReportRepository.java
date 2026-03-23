package org.example.bogo.domain.report.repository;

import org.example.bogo.domain.report.entity.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByProjectMemberId(Long memberId, Pageable pageable);
    Optional<Report> findByProjectId(Long projectId);
}
