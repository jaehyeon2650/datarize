## Datarize 기능 요약

### 핵심 기능
- Spring Boot 기반의 회원 가입 및 공연 좌석 조회/임시 배정 REST API 구현.
- 회원, 공연, 공연 시간, 좌석, 예약에 대한 JPA 엔티티 구성.
- 좌석 상태 흐름: AVAILABLE -> HOLD(만료) -> RESERVED(열거형만 존재, 확정 API 미구현).

### API 엔드포인트
- POST `/api/users`: 이름/생년월일 검증 후 회원 생성.
- GET `/api/shows/{concertTimeId}/seats`: 등급, 상태, 타입, 가격(계산 포함) 좌석 목록 조회.
- POST `/api/shows/{concertTimeId}/seats/hold`: 좌석 임시 배정(홀드) 요청.

### 예약 규칙(서비스 레이어에서 강제)
- AVAILABLE 상태 좌석만 홀드 가능; 좌석 조회에 비관적 락 사용.
- 1~5석만 홀드 가능.
- 커플석은 인접한 2석 단위(같은 행, 열 차이 1)로 선택해야 함.
- 같은 공연 시간의 기존 HOLD 예약은 신규 홀드 전에 정리.
- 홀드 만료 시간 5분; 만료 시 예약 삭제 및 좌석 상태 복원.

### 가격 정책
- 좌석 가격은 공연 등급별 가격 정보로 계산.
- 당일 첫 공연 시간은 10% 할인(`PriceCalculator`).

### 에러 처리 및 API 문서화
- `BusinessException`과 `ErrorCode`로 HTTP 상태/메시지 매핑.
- `ErrorController`에서 일관된 에러 응답 반환.
- OpenAPI 커스터마이징: 에러 응답 스키마 및 엔드포인트별 에러 코드 표기.

### 데이터/설정
- MySQL 데이터소스 설정 및 SQL 초기화/스키마 자동 생성.
- `init.sql`로 공연/등급 가격/공연 시간/좌석 맵 시드.

### 테스트
- 회원 검증, 가격 할인, 홀드 만료 정리, 예약 규칙에 대한 JPA 테스트 존재.
