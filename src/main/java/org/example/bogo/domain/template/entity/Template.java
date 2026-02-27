package org.example.bogo.domain.template.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Template {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "template_id",nullable = false)
    private Long id;

    @Column(name ="name",nullable = false)
    private String name;

    @Column(name="description",nullable = false)
    private String description;


}
