package org.example.datarize.performance.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import org.example.datarize.performance.domain.Seat;
import org.example.datarize.performance.domain.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findSeatsByConcertTimeId(final Long concertTimeId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Seat> findAllByIdIn(
            final List<Long> seatIds
    );

    @Query("""
        UPDATE Seat s
        SET s.status = :status
        WHERE s.id in :seatIds
    """
    )
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void updateSeatsStatus(
            final List<Long> seatIds,
            final SeatStatus status
    );
}
