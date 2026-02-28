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
}