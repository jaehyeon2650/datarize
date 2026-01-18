package org.example.datarize.performance.repository;

import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import org.example.datarize.member.domain.Member;
import org.example.datarize.performance.domain.ConcertTime;
import org.example.datarize.performance.domain.Reservation;
import org.example.datarize.performance.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Reservation> findAllByStatusAndUpdatedAtBefore(
            final ReservationStatus reservationStatus,
            final LocalDateTime expiredTime
    );

    @Query("""
                SELECT r
                FROM Reservation r
                JOIN FETCH r.seat s
                JOIN r.member m
                JOIN s.concertTime ct
                WHERE ct = :concertTime
                    AND m = :member
                    AND r.status = :status
            """
    )
    List<Reservation> findAllByConcertTimeAndStatusAndMember(
            final ReservationStatus status,
            final ConcertTime concertTime,
            final Member member
    );
}
