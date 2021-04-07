-- 그룹 권한 별 리포트 업로드 수 
# SELECT TA.AUTH_NAME , IFNULL(S.COUNT, 0) AS COUNT
# FROM TB_AUTH TA
# LEFT JOIN
# (
# SELECT T.AUTH_SEQ, COUNT(T.AUTH_SEQ) AS COUNT
# FROM (
# SELECT CASE
#         WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
#         WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
#         WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_SEQ
#         # 			WHEN TA.AUTH_DEPTH = 3 THEN UTA.AUTH_SEQ
#     END AS AUTH_SEQ
#     , CASE
#         WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_NAME
#         WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_NAME
#         WHEN TA.AUTH_DEPTH = 3 THEN UTA.AUTH_NAME
#     END AS AUTH_NAME
# FROM TB_REPORT TR
# INNER JOIN TB_AUTH TA
# ON TR.AUTH_SEQ = TA.AUTH_SEQ
# LEFT JOIN TB_AUTH UTA
# ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
# WHERE TR.REGISTRATION_DT BETWEEN '2020-09-24 00:00:00' AND '2020-11-30 23:59:59'
# # AND TR.USE_YN = 'Y'
# # AND TR.DEVICE = 'PC'
# # AND TUAL.DEVICE = 'MO'
# ) T
# GROUP BY T.AUTH_SEQ
# ) S
# ON TA.AUTH_SEQ = S.AUTH_SEQ
# WHERE TA.AUTH_DEPTH = 2
# ORDER BY TA.AUTH_SEQ ASC;





-- REPORT
-- REPORT 업로드 수
SELECT
#        TA.AUTH_DEPTH,
       CASE
           WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_NAME
           WHEN TA.AUTH_DEPTH = 3 THEN ''
           END AS 2DEPTH_NAME,
#        TA.AUTH_NAME,
       CASE
           WHEN TA.AUTH_DEPTH = 2 THEN ''
           WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_NAME
           END AS 3DEPTH_NAME,
       IFNULL(S.COUNT, 0) AS COUNT,
#        S.AUTH_DEPTH,
#        S.UPPER_AUTH_SEQ,
       CASE
           WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
           WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
           END AS UPPER_AUTH_SEQ
#        TA.AUTH_DEPTH
FROM TB_AUTH TA
         LEFT JOIN
     (
         SELECT T.AUTH_SEQ, COUNT(T.AUTH_SEQ) AS COUNT, T.AUTH_DEPTH, T.UPPER_AUTH_SEQ
         FROM (
                  SELECT CASE
                             WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
                             WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                             WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_SEQ
                             END AS AUTH_SEQ,
                         TA.AUTH_DEPTH,
                         CASE
                             WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                             WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
                             END AS UPPER_AUTH_SEQ
                  FROM TB_REPORT TR
                           INNER JOIN TB_AUTH TA
                                      ON TR.AUTH_SEQ = TA.AUTH_SEQ
                           LEFT JOIN TB_AUTH UTA
                                     ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
#                   WHERE TR.REGISTRATION_DT BETWEEN '2020-12-16 00:00:00' AND '2021-01-15 23:59:59'
#                   WHERE TR.REGISTRATION_DT BETWEEN '2021-01-16 00:00:00' AND '2021-02-15 23:59:59'
                  WHERE TR.REGISTRATION_DT BETWEEN '2021-02-16 00:00:00' AND '2021-03-15 23:59:59'
                    AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
#         AND TR.DEVICE = 'PC'
#         AND TR.DEVICE = 'MO'
              ) T
         GROUP BY T.AUTH_SEQ
     ) S
     ON TA.AUTH_SEQ = S.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
   OR TA.AUTH_DEPTH = 3
ORDER BY UPPER_AUTH_SEQ ASC;

-- 계정 수 추가
SELECT
CASE
    WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_NAME
    WHEN TA.AUTH_DEPTH = 3 THEN ''
    END AS 2DEPTH_NAME,
CASE
    WHEN TA.AUTH_DEPTH = 2 THEN ''
    WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_NAME
    END AS 3DEPTH_NAME,
IFNULL(S.COUNT, 0) AS COUNT,
# IFNULL(SUM(S.COUNT), 0) AS SUM_COUNT,
IFNULL(S.SEQ, 0) AS ACCOUNT_COUNT
,CASE
    WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
    WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
    END AS UPPER_AUTH_SEQ
# , TA.AUTH_SEQ
# , SUM(S.REGISTER_SEQ) AS ACCOUNT_COUNT
# ,IFNULL(S.REGISTER_SEQ, 0) AS COUNT
FROM TB_AUTH TA
         LEFT JOIN
     (
         SELECT B.AUTH_SEQ, SUM(B.COUNT) AS COUNT, COUNT(B.REGISTER_SEQ) AS SEQ, B.AUTH_DEPTH,
                B.UPPER_AUTH_SEQ
         FROM (
                  SELECT T.AUTH_SEQ,
                         COUNT(T.AUTH_SEQ) AS COUNT,
                         T.REGISTER_SEQ,
                         T.AUTH_DEPTH,
                         T.UPPER_AUTH_SEQ
                  FROM (
                           SELECT CASE
                                      WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
                                      WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                                      WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_SEQ
                                      END AS AUTH_SEQ,
                                  TA.AUTH_DEPTH,
                                  CASE
                                      WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                                      WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
                                      END AS UPPER_AUTH_SEQ
                                   ,
                                  TR.REGISTER_SEQ
                           FROM TB_REPORT TR
                                    INNER JOIN TB_AUTH TA
                                               ON TR.AUTH_SEQ = TA.AUTH_SEQ
                                    LEFT JOIN TB_AUTH UTA
                                              ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ

#                                                   WHERE TR.REGISTRATION_DT BETWEEN '2020-12-16 00:00:00' AND '2021-01-15 23:59:59'
#                                                   WHERE TR.REGISTRATION_DT BETWEEN '2021-01-16 00:00:00' AND '2021-02-15 23:59:59'
                           WHERE TR.REGISTRATION_DT BETWEEN '2021-02-16 00:00:00' AND '2021-03-15 23:59:59'
                             AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
                       ) T
                GROUP BY T.AUTH_SEQ, T.REGISTER_SEQ
              ) B
         GROUP BY B.AUTH_SEQ
     ) S
     ON TA.AUTH_SEQ = S.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
   OR TA.AUTH_DEPTH = 3
# GROUP BY TA.AUTH_SEQ
ORDER BY UPPER_AUTH_SEQ ASC;



95 - 8

SELECT TR.REPORT_NAME, TR.REGISTER_SEQ
FROM TB_REPORT TR
LEFT JOIN TB_AUTH TA on TR.AUTH_SEQ = TA.AUTH_SEQ
WHERE TR.REGISTRATION_DT BETWEEN '2020-12-16 00:00:00' AND '2021-01-15 23:59:59'
AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
;

SELECT *
FROM TB_AUTH TA
         LEFT JOIN
     (
         SELECT T.AUTH_SEQ, COUNT(T.AUTH_SEQ) AS COUNT, T.REGISTER_SEQ, T.AUTH_DEPTH, T.UPPER_AUTH_SEQ
         FROM (
                  SELECT CASE
                             WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
                             WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                             WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_SEQ
                             END AS AUTH_SEQ,
                         TA.AUTH_DEPTH,
                         CASE
                             WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                             WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
                             END AS UPPER_AUTH_SEQ
                          , TR.REGISTER_SEQ
                  FROM TB_REPORT TR
                           INNER JOIN TB_AUTH TA
                                      ON TR.AUTH_SEQ = TA.AUTH_SEQ
                           LEFT JOIN TB_AUTH UTA
                                     ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ

#                   WHERE TR.REGISTRATION_DT BETWEEN '2020-12-16 00:00:00' AND '2021-01-15 23:59:59'
#                   WHERE TR.REGISTRATION_DT BETWEEN '2021-01-16 00:00:00' AND '2021-02-15 23:59:59'
                  WHERE TR.REGISTRATION_DT BETWEEN '2021-02-16 00:00:00' AND '2021-03-15 23:59:59'
                    AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
#         AND TR.DEVICE = 'PC'
#         AND TR.DEVICE = 'MO'
              ) T
         GROUP BY T.AUTH_SEQ, T.REGISTER_SEQ
     ) S
     ON TA.AUTH_SEQ = S.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
   OR TA.AUTH_DEPTH = 3
;
