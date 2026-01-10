package org.example.datarize.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("이름이 1글자 이하인 경우 예외가 발생한다.")
    void validateName(final String name) {
        assertThatThrownBy(() -> Member.of(name, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 1글자 이상이여야합니다.");
    }

    @Test
    @DisplayName("생일이 미래인 경우 예외가 발생한다.")
    void validateBrith() {
        // given
        final LocalDate future = LocalDate.now().plusDays(1);
        // when & then
        assertThatThrownBy(() -> Member.of("이름", future))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("생일은 미래일 수 없습니다.");
    }
}
