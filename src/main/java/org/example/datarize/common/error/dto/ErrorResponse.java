package org.example.datarize.common.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "공통 에러 응답")
public record ErrorResponse(
        int status,
        String errorMessage
) {
}
