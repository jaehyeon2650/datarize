package org.example.datarize.performance.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.example.datarize.performance.domain.ConcertTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConcertTimeRepository extends JpaRepository<ConcertTime, Long> {

    @Query("""
            SELECT
                (count(st) = 0)
            FROM ConcertTime st
            WHERE st.concert.id = :concertId
              AND st.date = :date
              AND st.time < :time
            """)
    boolean isFirstShowTimeOfDate(
            final Long concertId,
            final LocalDate date,
            final LocalTime time
    );
}
