package org.example.bogo.domain.member.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinResponse {
    @JsonProperty("status")
    String status;
    @JsonProperty("message")
    String message;
    @JsonProperty("data")
    String data;

}