package org.example.bogo.domain.template.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Constrait {

    @Id
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String requiredFeature;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "json")
    private Map<String, Object> questions;

    // 관리자가 이후에 수정할 제약조건 사항
    public void setConstrait(String requiredFeature, Map<String, Object> questions) {
        this.requiredFeature = requiredFeature;
        this.questions = questions;
    }

    // 초기에 한번만 사용될 제약조건 생성 builder
    @Builder
    public Constrait(Long id, String requiredFeature, Map<String, Object> questions) {
        this.id = 1L;
        this.requiredFeature = requiredFeature;
        this.questions = questions;
    }
}