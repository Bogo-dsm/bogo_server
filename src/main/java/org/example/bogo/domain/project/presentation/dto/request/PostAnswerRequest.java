package org.example.bogo.domain.project.presentation.dto.request;

import java.util.List;

public record PostAnswerRequest(Long projectId,
                                List<Answer> answers) {

    public record Answer(
            int qId,
            String a
    ) {}
}

