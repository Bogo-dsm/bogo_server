package org.example.bogo.domain.member.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken
) {}