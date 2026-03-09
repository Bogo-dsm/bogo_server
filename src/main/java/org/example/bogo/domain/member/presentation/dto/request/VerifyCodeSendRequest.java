package org.example.bogo.domain.member.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyCodeSendRequest(
        @Email
        @NotBlank
        @Size(max = 50)
        String email

) {}
