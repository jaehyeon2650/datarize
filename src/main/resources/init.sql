INSERT INTO concert (id, name)
VALUES (1, '레미제라블'),
       (2, '오페라의 유령');

INSERT INTO concert_info (id, concert_id, grade, price)
VALUES (1, 1, 'VIP', 150000),
       (2, 1, 'R', 120000),
       (3, 1, 'S', 90000),
       (4, 1, 'A', 60000);
INSERT INTO concert_info (id, concert_id, grade, price)
VALUES (5, 2, 'VIP', 170000),
       (6, 2, 'R', 140000),
       (7, 2, 'S', 110000),
       (8, 2, 'A', 80000);

INSERT INTO concert_time (id, concert_id, date, time)
VALUES (1, 1, '2026-12-25', '10:00:00'),
       (2, 1, '2026-12-25', '14:00:00'),
       (3, 1, '2026-12-25', '19:00:00');
INSERT INTO concert_time (id, concert_id, date, time)
VALUES (4, 2, '2026-12-25', '11:00:00'),
       (5, 2, '2026-12-25', '17:00:00');

INSERT INTO seat (concert_time_id, seat_row, seat_col, grade, status, is_couple_seat)
SELECT st.concert_time_id,
       r,
       c,
       CASE
           WHEN r BETWEEN 1 AND 3 THEN 'VIP'
           WHEN r BETWEEN 4 AND 8 THEN 'R'
           WHEN r BETWEEN 9 AND 14 THEN 'S'
           ELSE 'A'
           END,
       'AVAILABLE',
       c BETWEEN 19 AND 20
FROM (SELECT 1 AS concert_time_id UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) st
         CROSS JOIN
     (SELECT 1 r
      UNION ALL
      SELECT 2
      UNION ALL
      SELECT 3
      UNION ALL
      SELECT 4
      UNION ALL
      SELECT 5
      UNION ALL
      SELECT 6
      UNION ALL
      SELECT 7
      UNION ALL
      SELECT 8
      UNION ALL
      SELECT 9
      UNION ALL
      SELECT 10
      UNION ALL
      SELECT 11
      UNION ALL
      SELECT 12
      UNION ALL
      SELECT 13
      UNION ALL
      SELECT 14
      UNION ALL
      SELECT 15
      UNION ALL
      SELECT 16
      UNION ALL
      SELECT 17
      UNION ALL
      SELECT 18
      UNION ALL
      SELECT 19
      UNION ALL
      SELECT 20) con_rows
         CROSS JOIN
     (SELECT 1 c
      UNION ALL
      SELECT 2
      UNION ALL
      SELECT 3
      UNION ALL
      SELECT 4
      UNION ALL
      SELECT 5
      UNION ALL
      SELECT 6
      UNION ALL
      SELECT 7
      UNION ALL
      SELECT 8
      UNION ALL
      SELECT 9
      UNION ALL
      SELECT 10
      UNION ALL
      SELECT 11
      UNION ALL
      SELECT 12
      UNION ALL
      SELECT 13
      UNION ALL
      SELECT 14
      UNION ALL
      SELECT 15
      UNION ALL
      SELECT 16
      UNION ALL
      SELECT 17
      UNION ALL
      SELECT 18
      UNION ALL
      SELECT 19
      UNION ALL
      SELECT 20) con_cols;

