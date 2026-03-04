package org.example.bogo.domain.member.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyEmailRequest(
        @Email
        @NotBlank
        @Size(max = 50)
        String email,

        @NotBlank
        @Size(min = 6, max = 6)
        String code
) {

}
