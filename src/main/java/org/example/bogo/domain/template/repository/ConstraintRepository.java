package org.example.bogo.domain.template.repository;

import org.example.bogo.domain.template.entity.Constraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ConstraintRepository extends JpaRepository<Constraint, Long> {

    @Query(value = "SELECT t.questions FROM constraint_tbl t WHERE t.id = :id", nativeQuery = true)
    List<Map<Long, String>> findQuestionsJsonById(Long id);
}
