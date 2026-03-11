package org.example.bogo.domain.template.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false, length = 50)
    private String tone;

    // AI 프롬포팅용 요약
    @Lob
    @Column(nullable = false)
    private String character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "constraint_id", nullable = false)
    private Constraint constraint;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Template(String name, String description, String tone, String character, Constraint constraint) {
        this.name = name;
        this.description = description;
        this.tone = tone;
        this.character = character;
        this.constraint = Objects.requireNonNull(constraint, "Constrait must not be null");
    }
}