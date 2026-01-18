package org.example.datarize.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    REQUIRED_BIRTH(HttpStatus.BAD_REQUEST, "birth는 필수입니다."),
    INVALID_BIRTH(HttpStatus.BAD_REQUEST, "birth 형식이 올바르지 않습니다. (yyyy-MM-dd)"),
    INVALID_BIRTH_NOT_FUTURE(HttpStatus.BAD_REQUEST, "생일은 미래일 수 없습니다."),
    INVALID_NAME_LENGTH(HttpStatus.BAD_REQUEST, "이름은 1글자 이상이여야합니다."),

    INVALID_CONCERT_TIME(HttpStatus.NOT_FOUND, "존재하지 않은 회차입니다."),
    INVALID_CONCERT_INFO(HttpStatus.NOT_FOUND, "가격 정보가 존재하지 않습니다."),
    INVALID_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않은 회원입니다."),

    INVALID_SIZE_RESERVATION(HttpStatus.BAD_REQUEST, "좌석을 최소 1개, 최대 5개까지 임시배정 가능합니다."),
    INVALID_SIZE_COUPLE_RESERVATION(HttpStatus.BAD_REQUEST, "커플석은 2개씩 선택해야 합니다."),
    INVALID_LOCATION_COUPLE_RESERVATION(HttpStatus.BAD_REQUEST, "커플석은 인접한 좌석으로 선택해야 합니다."),
    CANNOT_RESERVATION(HttpStatus.BAD_REQUEST, "예약할 수 없는 자리입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
