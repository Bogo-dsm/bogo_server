package org.example.bogo.domain.template.repository;

import org.example.bogo.domain.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findAll();

}
