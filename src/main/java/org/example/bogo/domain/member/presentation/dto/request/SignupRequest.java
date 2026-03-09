package org.example.bogo.domain.member.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank
        @Size(max = 20)
        String nickname,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8, max = 255)
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Password must include letters and numbers"
        )
        String password
) {}