package org.example.bogo.domain.template.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "constraint_tbl")
public class Constraint {

    @Id
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String requiredFeature;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "json")
    private List<Map<String, Object>> questions;

    // 관리자가 이후에 수정할 제약조건 사항
    public void setConstraint(String requiredFeature, List<Map<String, Object>> questions) {
        this.requiredFeature = Objects.requireNonNull(requiredFeature, "requiredFeature must not be null");
        this.questions = Objects.requireNonNull(questions, "questions must not be null");
    }

    // 초기에 한번만 사용될 제약조건 생성 builder
    @Builder
    public Constraint(Long id, String requiredFeature, List<Map<String, Object>> questions) {
        this.id = 1L;
        this.requiredFeature = requiredFeature;
        this.questions = questions;
    }
}