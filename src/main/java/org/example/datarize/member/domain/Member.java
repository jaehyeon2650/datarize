package org.example.datarize.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.datarize.common.error.BusinessException;
import org.example.datarize.common.error.ErrorCode;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birth;

    private Member(final String name, final LocalDate birth) {
        validateName(name);
        validateBirth(birth);
        this.name = name;
        this.birth = birth;
    }

    public static Member of(final String name, final LocalDate birth) {
        return new Member(name, birth);
    }

    private static void validateName(final String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_NAME_LENGTH);
        }
    }

    private static void validateBirth(final LocalDate birth) {
        if (birth.isAfter(LocalDate.now())) {
            throw new BusinessException(ErrorCode.INVALID_BIRTH_NOT_FUTURE);
        }
    }
}
