package org.example.bogo.domain.project.presentation.dto.response;

public record GetQuestionResponse(
        Long qId,
        String question
) {
}