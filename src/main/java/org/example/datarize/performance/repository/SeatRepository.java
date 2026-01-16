package org.example.datarize.performance.repository;

import java.util.List;
import org.example.datarize.performance.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findSeatsByConcertTimeId(final Long concertTimeId);
}
