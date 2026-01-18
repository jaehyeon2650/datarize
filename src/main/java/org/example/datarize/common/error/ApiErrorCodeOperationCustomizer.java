package org.example.datarize.common.error;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class ApiErrorCodeOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(
            final Operation operation,
            final HandlerMethod handlerMethod
    ) {
        final ApiErrorCodes apiErrorCodes = handlerMethod.getMethodAnnotation(ApiErrorCodes.class);

        if (apiErrorCodes == null) {
            return operation;
        }
        final Map<Integer, List<ErrorCode>> groupErrorCodes = groupErrorCodesByHttpStatus(apiErrorCodes);
        for (final Entry<Integer, List<ErrorCode>> entry : groupErrorCodes.entrySet()) {
            final int status = entry.getKey();
            final String descriptions = entry.getValue()
                    .stream()
                    .map(error -> "- " + error.name() + ": " + error.getMessage())
                    .collect(Collectors.joining("\n"));
            final ApiResponse apiResponse = new ApiResponse()
                    .description(descriptions)
                    .content(new Content().addMediaType(
                            "application/json",
                            new MediaType().schema(
                                    new Schema<>().$ref("#/components/schemas/ErrorResponse")
                            )
                    ));
            operation.getResponses().addApiResponse(
                    String.valueOf(status),
                    apiResponse
            );
        }
        return operation;
    }

    private LinkedHashMap<Integer, List<ErrorCode>> groupErrorCodesByHttpStatus(final ApiErrorCodes apiErrorCodes) {
        return Arrays.stream(apiErrorCodes.value())
                .collect(Collectors.groupingBy(
                        errorCode -> errorCode.getHttpStatus().value(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }
}
