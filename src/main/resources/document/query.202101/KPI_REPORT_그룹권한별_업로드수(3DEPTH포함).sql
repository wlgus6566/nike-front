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
-- REPORT 업로드 수  (3depth)
SELECT TA.AUTH_SEQ,
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
                        , TR.REGISTER_SEQ
                  FROM TB_REPORT TR
                           INNER JOIN TB_AUTH TA
                                      ON TR.AUTH_SEQ = TA.AUTH_SEQ
                           LEFT JOIN TB_AUTH UTA
                                     ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
                  WHERE TR.REGISTRATION_DT BETWEEN '2020-09-24 00:00:00' AND '2020-10-15 23:59:59'
#                   WHERE TR.REGISTRATION_DT BETWEEN '2020-10-16 00:00:00' AND '2020-11-15 23:59:59'
#                   WHERE TR.REGISTRATION_DT BETWEEN '2020-11-16 00:00:00' AND '2020-12-15 23:59:59'
                    AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
              ) T
         GROUP BY T.AUTH_SEQ
     ) S
     ON TA.AUTH_SEQ = S.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
   OR TA.AUTH_DEPTH = 3
ORDER BY UPPER_AUTH_SEQ ASC;


-- REPORT 업로드 수  (3depth & 계정)
# SELECT TA.AUTH_SEQ,
# #        TA.AUTH_DEPTH,
#        CASE
#            WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_NAME
#            WHEN TA.AUTH_DEPTH = 3 THEN ''
#            END AS 2DEPTH_NAME,
# #        TA.AUTH_NAME,
#        CASE
#            WHEN TA.AUTH_DEPTH = 2 THEN ''
#            WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_NAME
#            END AS 3DEPTH_NAME,
#        IFNULL(S.COUNT, 0) AS COUNT,
# #        S.AUTH_DEPTH,
# #        S.UPPER_AUTH_SEQ,
#        CASE
#            WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
#            WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
#            END AS UPPER_AUTH_SEQ
# #        TA.AUTH_DEPTH
# FROM TB_AUTH TA
#          LEFT JOIN
#      (
#          SELECT T.AUTH_SEQ, COUNT(T.AUTH_SEQ) AS COUNT, T.AUTH_DEPTH, T.UPPER_AUTH_SEQ
#          FROM (
#                   SELECT CASE
#                              WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
#                              WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
#                              WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_SEQ
#                              END AS AUTH_SEQ,
#                          TA.AUTH_DEPTH,
#                          CASE
#                              WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
#                              WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
#                              END AS UPPER_AUTH_SEQ
#                           , TR.REGISTER_SEQ
#                   FROM TB_REPORT TR
#                            INNER JOIN TB_AUTH TA
#                                       ON TR.AUTH_SEQ = TA.AUTH_SEQ
#                            LEFT JOIN TB_AUTH UTA
#                                      ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
#                   WHERE TR.REGISTRATION_DT BETWEEN '2020-09-24 00:00:00' AND '2020-10-15 23:59:59'
# #                   WHERE TR.REGISTRATION_DT BETWEEN '2020-10-16 00:00:00' AND '2020-11-15 23:59:59'
# #                   WHERE TR.REGISTRATION_DT BETWEEN '2020-11-16 00:00:00' AND '2020-12-15 23:59:59'
#                     AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
#               ) T
#          GROUP BY T.AUTH_SEQ
#      ) S
#      ON TA.AUTH_SEQ = S.AUTH_SEQ
# WHERE TA.AUTH_DEPTH = 2
#    OR TA.AUTH_DEPTH = 3
# ORDER BY UPPER_AUTH_SEQ ASC;
#



# SELECT TU.USER_SEQ, TU.USER_ID, T.*
# FROM TB_USER TU
# LEFT JOIN
#      (
#          SELECT T.AUTH_SEQ, COUNT(T.AUTH_SEQ) AS COUNT, T.AUTH_DEPTH, T.UPPER_AUTH_SEQ, T.REGISTER_SEQ
#          FROM (
#                   SELECT CASE
#                              WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
#                              WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
#                              WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_SEQ
#                              END AS AUTH_SEQ,
#                          TA.AUTH_DEPTH,
#                          CASE
#                              WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
#                              WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
#                              END AS UPPER_AUTH_SEQ
#                           , TR.REGISTER_SEQ
#                   FROM TB_REPORT TR
#                            INNER JOIN TB_AUTH TA
#                                       ON TR.AUTH_SEQ = TA.AUTH_SEQ
#                            LEFT JOIN TB_AUTH UTA
#                                      ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
#                   WHERE TR.REGISTRATION_DT BETWEEN '2020-09-24 00:00:00' AND '2020-10-15 23:59:59'
# #                   WHERE TR.REGISTRATION_DT BETWEEN '2020-10-16 00:00:00' AND '2020-11-15 23:59:59'
# #                   WHERE TR.REGISTRATION_DT BETWEEN '2020-11-16 00:00:00' AND '2020-12-15 23:59:59'
#                     AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
#               ) T
#          GROUP BY T.AUTH_SEQ
#      ) T
#      ON TU.USER_SEQ = T.REGISTER_SEQ
# ;




SELECT TA.AUTH_SEQ,
       CASE
           WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_NAME
           WHEN TA.AUTH_DEPTH = 3 THEN ''
           END AS 2DEPTH_NAME,
       CASE
           WHEN TA.AUTH_DEPTH = 2 THEN U.USER_ID
           WHEN TA.AUTH_DEPTH = 3 THEN ''
           END AS 2DEPTH_USER_ID,
       CASE
           WHEN TA.AUTH_DEPTH = 2 THEN ''
           WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_NAME
           END AS 3DEPTH_NAME,
       CASE
           WHEN TA.AUTH_DEPTH = 2 THEN ''
           WHEN TA.AUTH_DEPTH = 3 THEN U.USER_ID
           END AS 3DEPTH_USER_ID,
       IFNULL(U.COUNT, 0) AS COUNT,
       CASE
           WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
           WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
           END AS UPPER_AUTH_SEQ
FROM TB_AUTH TA
         LEFT JOIN
     (
          SELECT TU.USER_ID, IFNULL(S.REGISTER_SEQ, 0) AS REGISTER_SEQ, IFNULL(S.COUNT, 0) AS COUNT, TUA.AUTH_SEQ, TA.AUTH_DEPTH
          FROM TB_USER TU
            LEFT JOIN
            (
                SELECT T.REGISTER_SEQ, COUNT(T.REGISTER_SEQ) AS COUNT
                FROM (
                    SELECT TR.REGISTER_SEQ
                    FROM TB_REPORT TR
#                     WHERE TR.REGISTRATION_DT BETWEEN '2020-09-24 00:00:00' AND '2020-10-15 23:59:59'
#                     WHERE TR.REGISTRATION_DT BETWEEN '2020-10-16 00:00:00' AND '2020-11-15 23:59:59'
#                     WHERE TR.REGISTRATION_DT BETWEEN '2020-11-16 00:00:00' AND '2020-12-15 23:59:59'
                    WHERE TR.REGISTRATION_DT BETWEEN '2020-09-24 00:00:00' AND '2020-12-15 23:59:59'
                ) T
                GROUP BY T.REGISTER_SEQ
            ) S
            ON TU.USER_SEQ = S.REGISTER_SEQ
         LEFT JOIN TB_USER_AUTH TUA
             ON TU.USER_SEQ = TUA.USER_SEQ
         LEFT JOIN TB_AUTH TA
            ON TUA.AUTH_SEQ = TA.AUTH_SEQ
         WHERE TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3
     ) U
     ON TA.AUTH_SEQ = U.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
   OR TA.AUTH_DEPTH = 3
ORDER BY UPPER_AUTH_SEQ ASC;