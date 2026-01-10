package org.example.datarize.member.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberCreateRequestTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    @DisplayName("생일이 빈칸이거나 입력을 하지 않으면 예외가 발생한다.")
    void validateBirthBlank(final String birth) {
        assertThatThrownBy(() -> new MemberCreateRequest("a", birth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("birth는 필수입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2000:11:02", "2000:123:13", "2000-11-a"})
    @DisplayName("생일의 형식이 올바르지 않으면 예외가 발생한다.")
    void validateBirthInput(final String birth) {
        assertThatThrownBy(() -> new MemberCreateRequest("a", birth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("birth 형식이 올바르지 않습니다. (yyyy-MM-dd)");
    }
}
