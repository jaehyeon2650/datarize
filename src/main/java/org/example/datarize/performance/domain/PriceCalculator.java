package org.example.datarize.performance.domain;

import org.springframework.stereotype.Component;

@Component
public class PriceCalculator {

    public int calculateWithMorningDiscount(
            final ConcertInfo seat,
            final boolean isFirstShowTime
    ) {
        if (isFirstShowTime) {
            return applyDiscount(seat.getPrice(), 10);
        }
        return seat.getPrice();
    }

    private int applyDiscount(
            final int price,
            final int percent
    ) {
        return price * (100 - percent) / 100;
    }
}
