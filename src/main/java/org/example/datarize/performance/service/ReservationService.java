package org.example.datarize.performance.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.datarize.common.error.BusinessException;
import org.example.datarize.common.error.ErrorCode;
import org.example.datarize.member.domain.Member;
import org.example.datarize.member.repository.MemberRepository;
import org.example.datarize.performance.domain.ConcertTime;
import org.example.datarize.performance.domain.Reservation;
import org.example.datarize.performance.domain.ReservationStatus;
import org.example.datarize.performance.domain.Seat;
import org.example.datarize.performance.domain.SeatStatus;
import org.example.datarize.performance.dto.ReservationRequest;
import org.example.datarize.performance.dto.ReservationResponse;
import org.example.datarize.performance.repository.ConcertTimeRepository;
import org.example.datarize.performance.repository.ReservationRepository;
import org.example.datarize.performance.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationCleanUpService reservationCleanUpService;
    private final ReservationRepository reservationRepository;
    private final ConcertTimeRepository concertTimeRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public List<ReservationResponse> reservationSeats(
            final ReservationRequest request,
            final Long concertTimeId
    ) {
        reservationCleanUpService.clearExpiredReservations();
        final Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MEMBER));
        final ConcertTime concertTime = concertTimeRepository.findById(concertTimeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CONCERT_TIME));
        final List<Long> seatIds = request.seatIds();
        final List<Seat> seats = seatRepository.findAllByIdIn(seatIds);
        clearHoldReservationByMember(concertTime, member);
        validateSeat(seats);
        final LocalDateTime updateTime = LocalDateTime.now();
        final List<Reservation> newReservations = seats.stream()
                .map(seat -> {
                    seat.changeSeatStatus(SeatStatus.HOLD);
                    return new Reservation(member, seat, ReservationStatus.HOLD, updateTime);
                })
                .toList();
        reservationRepository.saveAll(newReservations);
        return newReservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    private void validateSeat(final List<Seat> seats) {
        validateSeatStatus(seats);
        validateReservationSize(seats);
        validateCoupleSeats(seats);
    }

    private void validateReservationSize(final List<Seat> seats) {
        if (seats.isEmpty() || seats.size() > 5) {
            throw new BusinessException(ErrorCode.INVALID_SIZE_RESERVATION);
        }
    }

    private void validateCoupleSeats(final List<Seat> seats) {
        final List<Seat> coupleSeats = seats.stream()
                .filter(Seat::isCoupleSeat)
                .sorted(Comparator.comparingInt(Seat::getRow)
                                .thenComparing(Seat::getCol))
                .toList();
        if (coupleSeats.size() % 2 != 0) {
            throw new BusinessException(ErrorCode.INVALID_SIZE_COUPLE_RESERVATION);
        }
        for (int i = 0; i < coupleSeats.size(); i += 2) {
            final Seat left = coupleSeats.get(i);
            final Seat right = coupleSeats.get(i + 1);
            if (!left.isCoupleWith(right)) {
                throw new BusinessException(ErrorCode.INVALID_LOCATION_COUPLE_RESERVATION);
            }
        }
    }

    private void validateSeatStatus(final List<Seat> seats) {
        for (final Seat seat : seats) {
            if (seat.isNotAvailable()) {
                throw new BusinessException(ErrorCode.CANNOT_RESERVATION);
            }
        }
    }

    private void clearHoldReservationByMember(
            final ConcertTime concertTime,
            final Member member
    ) {
        final List<Reservation> reservations = reservationRepository.findAllByConcertTimeAndStatusAndMember(
                ReservationStatus.HOLD,
                concertTime,
                member
        );
        reservationRepository.deleteAll(reservations);
        reservations
                .forEach(reservation -> reservation.getSeat().changeSeatStatus(SeatStatus.AVAILABLE));
    }
}
