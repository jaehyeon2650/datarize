package org.example.datarize.member.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record MemberCreateRequest(
        String name,
        String birth
) {
    private static final DateTimeFormatter BIRTH_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public MemberCreateRequest {
        if (birth == null || birth.isBlank()) {
            throw new IllegalArgumentException("birth는 필수입니다.");
        }

        try {
            LocalDate.parse(birth, BIRTH_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("birth 형식이 올바르지 않습니다. (yyyy-MM-dd)");
        }
    }

    public LocalDate getBirth() {
        return LocalDate.parse(birth, BIRTH_FORMAT);
    }
}
