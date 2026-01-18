package org.example.datarize.performance.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.datarize.member.domain.Member;
import org.example.datarize.performance.domain.ConcertTime;
import org.example.datarize.performance.domain.Reservation;
import org.example.datarize.performance.domain.ReservationStatus;
import org.example.datarize.performance.domain.Seat;
import org.example.datarize.performance.domain.SeatStatus;
import org.example.datarize.performance.repository.ReservationRepository;
import org.example.datarize.performance.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationCleanUpService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void clearExpiredReservations() {
        final LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(5);

        final List<Reservation> expiredReservations = reservationRepository.findAllByStatusAndUpdatedAtBefore(
                ReservationStatus.HOLD,
                expiredTime
        );
        final List<Long> updateSeatIds = expiredReservations.stream()
                .map(Reservation::getSeat)
                .map(Seat::getId)
                .toList();
        reservationRepository.deleteAll(expiredReservations);
        seatRepository.updateSeatsStatus(updateSeatIds, SeatStatus.AVAILABLE);
    }
}
