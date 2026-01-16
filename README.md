## 🖥️ API 명세

| 기능 | Method | Endpoint (예시) |
| --- | --- | --- |
| 사용자 등록 | POST | `/api/users` |
| 좌석 조회 | GET | `/api/shows/{showId}/seats` |
| 좌석 임시 배정 | POST | `/api/shows/{showId}/seats/hold` |
| 예약 확정 | POST | `/api/reservations` |
| 내 예약 조회 | GET | `/api/users/{userId}/reservations` |
| 예약 취소 | DELETE | `/api/reservations/{reservationId}` |

### 1. 사용자 등록(POST, "/api/users")
- 이름과 생년월일을 입력하여 사용자를 등록합니다. [X]
- 등록된 사용자에게는 고유한 사용자 ID가 부여됩니다. [X]
- 동일한 이름과 생년월일로 중복 등록이 가능합니다. (동명이인 허용) [X]
- 이름은 1자 이상이어야 합니다. [X]
- 생년월일은 유효한 날짜여야 하며, 미래 날짜는 허용되지 않습니다. [X]

### 2. 좌석 조회(GET, "/api/shows/{showId}/seats")
- 전체 좌석 목록과 각 좌석의 상태를 반환합니다. [X]
- 좌석 상태: 예약 가능 / 임시 배정 / 예약 완료 [X]
- 각 좌석의 등급, 유형(일반석/커플석), 가격 정보를 포함합니다. [X]
