package org.example.bogo.domain.member.presentation.dto.response;

public record LoginResponse<T>(
        String status,
        String message,
        T data
) {}