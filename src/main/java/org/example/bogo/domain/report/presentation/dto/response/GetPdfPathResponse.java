package org.example.bogo.domain.report.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetPdfPathResponse(
        @JsonProperty("pdf_path")
        String pdfPath
) {
}
