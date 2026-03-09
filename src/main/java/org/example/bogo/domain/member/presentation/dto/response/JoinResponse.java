package org.example.bogo.domain.member.presentation.dto.response;

public record JoinResponse (
        String status,
        String message,
        String name
) { }