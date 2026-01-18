package org.example.datarize.performance.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.datarize.performance.dto.ReservationRequest;
import org.example.datarize.performance.dto.ReservationResponse;
import org.example.datarize.performance.dto.SeatInfoResponse;
import org.example.datarize.performance.service.ConcertService;
import org.example.datarize.performance.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConcertController implements ConcertApiDocs {

    private final ConcertService concertService;
    private final ReservationService reservationService;

    @GetMapping("/api/shows/{concertTimeId}/seats")
    public ResponseEntity<List<SeatInfoResponse>> readSeats(@PathVariable final Long concertTimeId) {
        final List<SeatInfoResponse> responses = concertService.readAllSeats(concertTimeId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/api/shows/{concertTimeId}/seats/hold")
    public ResponseEntity<List<ReservationResponse>> reservationSeats(
            @RequestBody final ReservationRequest request,
            @PathVariable Long concertTimeId
    ) {
        final List<ReservationResponse> responses = reservationService.reservationSeats(request, concertTimeId);
        return ResponseEntity.ok(responses);
    }
}
