package org.example.datarize.performance.dto;

import org.example.datarize.performance.domain.Grade;
import org.example.datarize.performance.domain.Seat;
import org.example.datarize.performance.domain.SeatStatus;

public record SeatInfoResponse(
        Long seatId,
        int row,
        int col,
        Grade grade,
        SeatStatus status,
        String type,
        int price
) {
    public static SeatInfoResponse of(
            final Seat seat,
            final int price
    ) {
        final String type = seat.isCoupleSeat() ? "커플석" : "일반석";
        return new SeatInfoResponse(
                seat.getId(), seat.getRow(), seat.getCol(), seat.getGrade(), seat.getStatus(), type, price);
    }
}
