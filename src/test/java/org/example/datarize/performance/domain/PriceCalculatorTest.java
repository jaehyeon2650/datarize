package org.example.datarize.performance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceCalculatorTest {

    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    @DisplayName("조조 할인이 가능한 경우 10퍼센트를 할인한다.")
    void discountMorning() {
        // given
        final Concert concert = new Concert("concert");
        final ConcertInfo concertInfo = new ConcertInfo(concert, Grade.A, 10000);
        // when
        final int price = priceCalculator.calculateWithMorningDiscount(concertInfo, true);
        // then
        Assertions.assertThat(price).isEqualTo(9000);
    }

    @Test
    @DisplayName("조조 할인이 불가능한 경우 원가를 반환한다.")
    void noDiscountMorning() {
        // given
        final Concert concert = new Concert("concert");
        final ConcertInfo concertInfo = new ConcertInfo(concert, Grade.A, 10000);
        // when
        final int price = priceCalculator.calculateWithMorningDiscount(concertInfo, false);
        // then
        Assertions.assertThat(price).isEqualTo(10000);
    }
}
