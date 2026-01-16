package org.example.datarize.performance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.example.datarize.performance.domain.Concert;
import org.example.datarize.performance.domain.ConcertInfo;
import org.example.datarize.performance.domain.ConcertTime;
import org.example.datarize.performance.domain.Grade;
import org.example.datarize.performance.domain.PriceCalculator;
import org.example.datarize.performance.domain.Seat;
import org.example.datarize.performance.domain.SeatStatus;
import org.example.datarize.performance.dto.SeatInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ConcertService.class, PriceCalculator.class})
class ConcertServiceTest {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("존재하지 않은 시간에 대해 예외가 발생한다.")
    void invalidTime() {
        assertThatThrownBy(() -> concertService.readAllSeats(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않은 시간입니다.");
    }

    @Test
    @DisplayName("해당 시간에 대한 정보를 반환한다.")
    void readAllSeats() {
        // given
        final Concert concert = new Concert("concert");
        final ConcertInfo concertInfo1 = new ConcertInfo(concert, Grade.VIP, 40000);
        final ConcertInfo concertInfo2 = new ConcertInfo(concert, Grade.A, 30000);
        final ConcertInfo concertInfo3 = new ConcertInfo(concert, Grade.R, 20000);
        final ConcertInfo concertInfo4 = new ConcertInfo(concert, Grade.S, 10000);
        final ConcertTime concertTime1 = new ConcertTime(concert, LocalDate.of(2000, 11, 2), LocalTime.of(10, 0));
        final ConcertTime concertTime2 = new ConcertTime(concert, LocalDate.of(2000, 11, 2), LocalTime.of(9, 0));
        final Seat seat1 = new Seat(concertTime1, 1, 1, Grade.VIP, SeatStatus.RESERVED, true);
        final Seat seat2 = new Seat(concertTime1, 2, 1, Grade.R, SeatStatus.AVAILABLE, false);
        final Seat seat3 = new Seat(concertTime2, 3, 1, Grade.VIP, SeatStatus.RESERVED, true);
        em.persist(concert);
        em.persist(concertInfo1);
        em.persist(concertInfo2);
        em.persist(concertInfo3);
        em.persist(concertInfo4);
        em.persist(concertTime1);
        em.persist(concertTime2);
        em.persist(seat1);
        em.persist(seat2);
        em.persist(seat3);
        em.flush();
        em.clear();
        // when
        final List<SeatInfoResponse> responses = concertService.readAllSeats(concertTime1.getId());
        // then
        assertThat(responses)
                .containsExactlyInAnyOrder(
                        new SeatInfoResponse(seat1.getId(), 1, 1, Grade.VIP, SeatStatus.RESERVED, "커플석", 40000),
                        new SeatInfoResponse(seat2.getId(), 2, 1, Grade.R, SeatStatus.AVAILABLE, "일반석", 20000)
                );
    }
}
