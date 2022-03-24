-- 계정별 주문 수
SELECT TA.AUTH_NAME, IFNULL(S.COUNT, 0) AS COUNT
FROM TB_AUTH TA
         LEFT JOIN
     (
         SELECT T.AUTH_SEQ, COUNT(T.AUTH_SEQ) AS COUNT
         FROM (
                  SELECT CASE
                             WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
                             WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                             WHEN TA.AUTH_DEPTH = 3 THEN UTA.AUTH_SEQ
                             END AS AUTH_SEQ
                  FROM TB_ORDER TBO
                           INNER JOIN TB_USER TU
                                      ON TBO.REGISTER_SEQ = TU.USER_SEQ
                           INNER JOIN TB_USER_AUTH TUA
                                      ON TU.USER_SEQ = TUA.USER_SEQ
                           INNER JOIN TB_AUTH TA
                                      ON TUA.AUTH_SEQ = TA.AUTH_SEQ
                           LEFT JOIN TB_AUTH UTA
                                     ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
-- #                   WHERE TBO.REGISTRATION_DT BETWEEN '2021-11-16 00:00:00' AND '2021-12-15 23:59:59'
                  WHERE TBO.REGISTRATION_DT BETWEEN '2022-02-16 00:00:00' AND '2022-03-15 23:59:59'
              ) T
         GROUP BY T.AUTH_SEQ
     ) S
     ON TA.AUTH_SEQ = S.AUTH_SEQ
-- # WHERE TA.AUTH_DEPTH = 1
WHERE TA.AUTH_DEPTH = 2
ORDER BY TA.AUTH_SEQ;