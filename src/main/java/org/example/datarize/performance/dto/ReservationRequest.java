package org.example.datarize.performance.dto;

import java.util.List;

public record ReservationRequest(
        Long memberId,
        List<Long> seatIds
) {
}
