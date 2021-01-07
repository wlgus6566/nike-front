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
SELECT TA.AUTH_NAME , IFNULL(S.COUNT, 0) AS COUNT
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
        FROM TB_REPORT TR
        INNER JOIN TB_AUTH TA
        ON TR.AUTH_SEQ = TA.AUTH_SEQ
        LEFT JOIN TB_AUTH UTA
        ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
#         WHERE TR.REGISTRATION_DT BETWEEN '2020-09-24 00:00:00' AND '2020-10-15 23:59:59'
#         WHERE TR.REGISTRATION_DT BETWEEN '2020-10-16 00:00:00' AND '2020-11-15 23:59:59'
#         WHERE TR.REGISTRATION_DT BETWEEN '2020-11-16 00:00:00' AND '2020-12-15 23:59:59'
        WHERE TR.REGISTRATION_DT BETWEEN '2020-09-24 00:00:00' AND '2020-12-15 23:59:59'
#         AND TA.AUTH_DEPTH = 2
#         AND TA.AUTH_DEPTH = 3
    ) T
    GROUP BY T.AUTH_SEQ
) S
ON TA.AUTH_SEQ = S.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
ORDER BY TA.AUTH_SEQ ASC;