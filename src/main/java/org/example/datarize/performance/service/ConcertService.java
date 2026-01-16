package org.example.datarize.performance.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.datarize.performance.domain.Concert;
import org.example.datarize.performance.domain.ConcertInfo;
import org.example.datarize.performance.domain.ConcertTime;
import org.example.datarize.performance.domain.Grade;
import org.example.datarize.performance.domain.PriceCalculator;
import org.example.datarize.performance.domain.Seat;
import org.example.datarize.performance.dto.SeatInfoResponse;
import org.example.datarize.performance.repository.ConcertInfoRepository;
import org.example.datarize.performance.repository.ConcertTimeRepository;
import org.example.datarize.performance.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConcertService {

    private final SeatRepository seatRepository;
    private final ConcertTimeRepository concertTimeRepository;
    private final ConcertInfoRepository concertInfoRepository;
    private final PriceCalculator priceCalculator;

    public List<SeatInfoResponse> readAllSeats(final Long concertTimeId) {
        // TODO : 좌석 상태 업데이트
        final ConcertTime concertTime = concertTimeRepository.findById(concertTimeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 시간입니다."));
        final Map<Grade, ConcertInfo> showInfos = findShowInfos(concertTime.getConcert());
        final List<Seat> seats = seatRepository.findSeatsByConcertTimeId(concertTimeId);
        final boolean isFirstShowTime = concertTimeRepository.isFirstShowTimeOfDate(
                concertTime.getConcert().getId(),
                concertTime.getDate(),
                concertTime.getTime()
        );

        return seats.stream()
                .map(seat -> {
                    final ConcertInfo concertInfo = showInfos.get(seat.getGrade());
                    if (concertInfo == null) {
                        throw new IllegalArgumentException("가격 정보가 존재하지 않습니다.");
                    }

                    return SeatInfoResponse.of(
                            seat,
                            priceCalculator.calculateWithMorningDiscount(
                                    concertInfo,
                                    isFirstShowTime
                            )
                    );
                })
                .toList();

    }

    private Map<Grade, ConcertInfo> findShowInfos(final Concert concert) {
        final List<ConcertInfo> concertInfos = concertInfoRepository.findAllByConcertId(concert.getId());
        return concertInfos.stream()
                .collect(Collectors.toMap(
                        ConcertInfo::getGrade,
                        Function.identity()
                ));
    }
}
