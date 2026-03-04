package org.example.bogo.domain.member.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank
        @Size(max = 50)
        String email,

        @NotBlank
        @Size(min = 4, max = 255)
        String password
) {
}
