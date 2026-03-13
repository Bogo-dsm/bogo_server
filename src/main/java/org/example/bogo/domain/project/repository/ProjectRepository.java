package org.example.bogo.domain.project.repository;

import org.example.bogo.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
