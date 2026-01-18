package org.example.datarize.performance.repository;

import java.util.List;
import org.example.datarize.performance.domain.ConcertInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertInfoRepository extends JpaRepository<ConcertInfo, Long> {

    List<ConcertInfo> findAllByConcertId(final Long concertId);
}
