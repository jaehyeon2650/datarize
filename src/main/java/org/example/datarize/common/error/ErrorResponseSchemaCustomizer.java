package org.example.datarize.common.error;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.OpenAPI;
import org.example.datarize.common.error.dto.ErrorResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseSchemaCustomizer implements OpenApiCustomizer {

    @Override
    public void customise(final OpenAPI openApi) {
        ModelConverters.getInstance()
                .read(ErrorResponse.class)
                .forEach((name, schema) -> {
                    openApi.getComponents().addSchemas(name, schema);
                });
    }
}
