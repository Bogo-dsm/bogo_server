package org.example.bogo.global;

public record APIResponse<T>(
        String status,
        String message,
        T data
) {}