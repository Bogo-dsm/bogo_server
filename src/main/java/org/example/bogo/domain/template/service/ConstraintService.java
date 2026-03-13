package org.example.bogo.domain.template.service;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.template.entity.Constraint;
import org.example.bogo.domain.template.presentation.dto.request.AddConstraintRequest;
import org.example.bogo.domain.template.repository.ConstraintRepository;
import org.example.bogo.global.APIResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstraintService {
    private final ConstraintRepository constraintRepository;

    public APIResponse<?> post(AddConstraintRequest data) {
        Constraint constraint = Constraint.builder()
                .id(1L)
                .questions(data.questions())
                .requiredFeature(data.requiredFeature())
                .build();
        constraintRepository.save(constraint);

        return new APIResponse<>(
                "CREATED",
                "Successfully created constraint",
                ""
        );
    }

}
