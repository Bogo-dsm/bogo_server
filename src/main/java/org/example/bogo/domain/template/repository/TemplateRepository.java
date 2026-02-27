package org.example.bogo.domain.template.repository;

import org.example.bogo.domain.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template,Long> {

    boolean existsById(Long id);
}