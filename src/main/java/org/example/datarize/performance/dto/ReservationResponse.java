package org.example.datarize.performance.dto;

import org.example.datarize.performance.domain.Reservation;
import org.example.datarize.performance.domain.ReservationStatus;

public record ReservationResponse(
        Long reservationId,
        Long seatId,
        ReservationStatus reservationStatus
) {
    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getSeat().getId(), reservation.getStatus());
    }
}
