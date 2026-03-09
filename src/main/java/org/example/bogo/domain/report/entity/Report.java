package org.example.bogo.domain.report.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bogo.domain.project.entity.Project;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "report")
@Getter
public class Report {
    @Id
    private Long id; // 프로젝트의 식별키와 동일한 값을 가짐

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId // Report의 id를 Project의 id로 사용
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "pdf_path", length = 2000)
    private String pdfPath;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
