package org.example.datarize.performance.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ConcertTime concertTime;

    @Column(name = "seat_row")
    private int row;

    @Column(name = "seat_col")
    private int col;

    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @Enumerated(value = EnumType.STRING)
    private SeatStatus status;

    private boolean isCoupleSeat;

    public Seat(
            final ConcertTime concertTime,
            final int row,
            final int col,
            final Grade grade,
            final SeatStatus status,
            final boolean isCoupleSeat
    ) {
        this.concertTime = concertTime;
        this.row = row;
        this.col = col;
        this.grade = grade;
        this.status = status;
        this.isCoupleSeat = isCoupleSeat;
    }
}
