package org.example.datarize.performance.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.example.datarize.member.domain.Member;
import org.example.datarize.performance.domain.Concert;
import org.example.datarize.performance.domain.ConcertTime;
import org.example.datarize.performance.domain.Grade;
import org.example.datarize.performance.domain.Reservation;
import org.example.datarize.performance.domain.ReservationStatus;
import org.example.datarize.performance.domain.Seat;
import org.example.datarize.performance.domain.SeatStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.transaction.TestTransaction;

@DataJpaTest
@Import(ReservationCleanUpService.class)
class ReservationCleanUpServiceTest {

    @Autowired
    private ReservationCleanUpService reservationCleanUpService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("5분이 만료된 좌석 배정은 삭제가 되고, 좌석의 상태가 좌석 배정 가능한 상태가 된다.")
    void clearExpiredReservations() {
        // given
        final LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(6);
        final LocalDateTime notExpiredTime = LocalDateTime.now().minusMinutes(2);
        final Member member = Member.of("member", LocalDate.now());
        final Concert concert = new Concert("concert");
        final ConcertTime concertTime = new ConcertTime(concert, LocalDate.now(), LocalTime.now());
        final Seat seat1 = new Seat(concertTime, 1, 1, Grade.VIP, SeatStatus.HOLD, false);
        final Reservation reservation1 = new Reservation(member, seat1, ReservationStatus.HOLD, expiredTime);
        final Seat seat2 = new Seat(concertTime, 2, 1, Grade.VIP, SeatStatus.HOLD, false);
        final Reservation reservation2 = new Reservation(member, seat2, ReservationStatus.HOLD, notExpiredTime);
        em.persist(member);
        em.persist(concert);
        em.persist(concertTime);
        em.persist(seat1);
        em.persist(reservation1);
        em.persist(seat2);
        em.persist(reservation2);
        em.flush();
        TestTransaction.flagForCommit();
        TestTransaction.end();
        // when
        reservationCleanUpService.clearExpiredReservations();
        TestTransaction.start();
        final Reservation deletedReservation = em.find(Reservation.class, reservation1.getId());
        final Reservation remainReservation = em.find(Reservation.class, reservation2.getId());
        final Seat changedSeat = em.find(Seat.class, seat1.getId());
        final Seat remainSeat = em.find(Seat.class, seat2.getId());
        // then
        assertThat(deletedReservation).isNull();
        assertThat(remainReservation).isNotNull();
        assertThat(changedSeat.getStatus()).isEqualTo(SeatStatus.AVAILABLE);
        assertThat(remainSeat.getStatus()).isEqualTo(SeatStatus.HOLD);
    }
}
