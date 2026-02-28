package org.example.bogo.domain.template.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

    @Lob
    @Column(columnDefinition = "TEXT")
    private String character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "constraint_id", nullable = false)
    private Constrait constraint;
}