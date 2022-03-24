-- ATF_파트너별 상세보기 (3depth)
SELECT TA.AUTH_NAME, IFNULL(S.2DEPTH, 0) AS '2DEPTH', IFNULL(S.3DEPTH, 0) AS '3DEPTH'
FROM TB_AUTH TA
LEFT JOIN
(
    SELECT T.AUTH_SEQ,
        SUM(CASE WHEN T.AUTH_DEPTH = 2 THEN 1 ELSE 0 END ) '2DEPTH',
        SUM(CASE WHEN T.AUTH_DEPTH = 3 THEN 1 ELSE 0 END ) '3DEPTH'
    FROM (
        SELECT 	CASE
            WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
            WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
            WHEN TA.AUTH_DEPTH = 3 THEN UTA.AUTH_SEQ
            END AS AUTH_SEQ, TA.AUTH_DEPTH
        FROM TB_USER_ACTION_LOG TUAL
        INNER JOIN TB_USER TU
        ON TUAL.USER_SEQ = TU.USER_SEQ
        INNER JOIN TB_USER_AUTH TUA
        ON TU.USER_SEQ = TUA.USER_SEQ
        INNER JOIN TB_AUTH TA
        ON TUA.AUTH_SEQ = TA.AUTH_SEQ
        LEFT JOIN TB_AUTH UTA
        ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
        WHERE TUAL.METHOD_SIGNATURE = 'findContents'
            AND TUAL.METHOD_TYPE_NAME = 'GET'
#              AND TUAL.URL LIKE '%ASSET%'
#              AND TUAL.URL LIKE '%TOOLKIT%'
             AND TUAL.URL LIKE '%FOUNDATION%'
            AND TUAL.REGISTRATION_DT BETWEEN '2022-02-16 00:00:00' AND '2022-03-15 23:59:59'
            AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
    ) T
    GROUP BY T.AUTH_SEQ
) S
ON TA.AUTH_SEQ = S.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
ORDER BY TA.AUTH_SEQ

;