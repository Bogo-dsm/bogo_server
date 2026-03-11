package org.example.bogo.domain.template.repository;

import org.example.bogo.domain.template.entity.Constraint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstraintRepository extends JpaRepository<Constraint, Long> {
}
