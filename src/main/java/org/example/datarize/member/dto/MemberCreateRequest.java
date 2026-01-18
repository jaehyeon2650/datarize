package org.example.datarize.member.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.example.datarize.common.error.BusinessException;
import org.example.datarize.common.error.ErrorCode;

public record MemberCreateRequest(
        String name,
        String birth
) {
    private static final DateTimeFormatter BIRTH_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public MemberCreateRequest {
        if (birth == null || birth.isBlank()) {
            throw new BusinessException(ErrorCode.REQUIRED_BIRTH);
        }

        try {
            LocalDate.parse(birth, BIRTH_FORMAT);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ErrorCode.INVALID_BIRTH);
        }
    }

    public LocalDate getBirth() {
        return LocalDate.parse(birth, BIRTH_FORMAT);
    }
}
