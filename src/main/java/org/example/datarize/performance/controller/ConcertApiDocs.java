package org.example.datarize.performance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.example.datarize.common.error.ApiErrorCodes;
import org.example.datarize.common.error.ErrorCode;
import org.example.datarize.performance.dto.ReservationRequest;
import org.example.datarize.performance.dto.ReservationResponse;
import org.example.datarize.performance.dto.SeatInfoResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "concert", description = "좌석 예약 API")
public interface ConcertApiDocs {

    @Operation(summary = "좌석 현황 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좌석 현황 조회 성공")
    })
    @ApiErrorCodes(value = {
            ErrorCode.INVALID_CONCERT_TIME,
            ErrorCode.INVALID_CONCERT_INFO
    })
    ResponseEntity<List<SeatInfoResponse>> readSeats(final Long concertTimeId);


    @Operation(summary = "좌석 임시 배정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좌석 임시 배정 성공")
    })
    @ApiErrorCodes(value = {
            ErrorCode.INVALID_MEMBER,
            ErrorCode.INVALID_CONCERT_TIME,
            ErrorCode.INVALID_SIZE_RESERVATION,
            ErrorCode.INVALID_SIZE_COUPLE_RESERVATION,
            ErrorCode.INVALID_LOCATION_COUPLE_RESERVATION,
            ErrorCode.CANNOT_RESERVATION
    })
    ResponseEntity<List<ReservationResponse>> reservationSeats(
            final ReservationRequest request,
            Long concertTimeId
    );
}
