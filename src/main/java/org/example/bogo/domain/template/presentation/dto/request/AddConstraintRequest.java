package org.example.bogo.domain.template.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record AddConstraintRequest(
        @JsonProperty("required_feature")
        String requiredFeature,
        List<Map<String, Object>> questions
)
{ }
