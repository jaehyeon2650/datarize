package org.example.datarize.performance.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.datarize.performance.dto.SeatInfoResponse;
import org.example.datarize.performance.service.ConcertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping("/api/shows/{concertTimeId}/seats")
    private List<SeatInfoResponse> readSeats(@PathVariable Long concertTimeId) {
        return concertService.readAllSeats(concertTimeId);
    }
}
