package org.example.bogo.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bogo.domain.member.entity.Member;
import org.example.bogo.domain.template.entity.Template;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title", length = 50)
    private String title;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> answers;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id")
    private Template template;

    @Builder
    public Project(String title, LocalDateTime createdAt, Map<String, Object> answers, Member member, Template template) {
        this.title = title;
        this.createdAt = createdAt;
        this.answers = answers;
        this.member = member;
        this.template = template;
    }
}
