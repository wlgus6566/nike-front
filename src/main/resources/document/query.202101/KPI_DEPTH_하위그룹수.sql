-- 2뎁스 그룹 수
SELECT TA.AUTH_NAME, IFNULL(BTA.COUNT, 0) +1 AS COUNT
FROM TB_AUTH TA
LEFT JOIN
(
	SELECT
	       UPPER_AUTH_SEQ
	     , COUNT(UPPER_AUTH_SEQ) AS COUNT
	FROM TB_AUTH
	WHERE AUTH_DEPTH = 2
	GROUP BY UPPER_AUTH_SEQ
) BTA
ON TA.AUTH_SEQ = BTA.UPPER_AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
ORDER BY TA.AUTH_SEQ ASC
;

-- 3DEPTH 그룹수
SELECT TA.AUTH_NAME, IFNULL(BTA.COUNT, 0) AS COUNT
FROM TB_AUTH TA
LEFT JOIN
(
	SELECT
	       UPPER_AUTH_SEQ
	     , COUNT(UPPER_AUTH_SEQ) AS COUNT
	FROM TB_AUTH 
	WHERE AUTH_DEPTH = 3
# 	  AND USE_YN = 'Y'
	GROUP BY UPPER_AUTH_SEQ
) BTA
ON TA.AUTH_SEQ = BTA.UPPER_AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
ORDER BY TA.AUTH_SEQ ASC
;

-- 3DEPTH 계정 수
SELECT TA.AUTH_NAME, IFNULL(BTA.COUNT, 0) AS COUNT
FROM TB_AUTH TA
         LEFT JOIN
     (
         SELECT TA.UPPER_AUTH_SEQ, COUNT(TA.UPPER_AUTH_SEQ) AS COUNT
         FROM TB_USER_AUTH TUA
                  LEFT JOIN TB_AUTH TA on TUA.AUTH_SEQ = TA.AUTH_SEQ
         WHERE TA.AUTH_DEPTH = 3
         GROUP BY UPPER_AUTH_SEQ
     ) BTA
     ON TA.AUTH_SEQ = BTA.UPPER_AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
ORDER BY TA.AUTH_SEQ ASC
;


-- 2DEPTH 계정 수
SELECT TA.AUTH_NAME, IFNULL(BTA.COUNT, 0) AS COUNT
FROM TB_AUTH TA
         LEFT JOIN
     (
         SELECT TA.AUTH_SEQ, COUNT(TA.UPPER_AUTH_SEQ) AS COUNT
         FROM TB_USER_AUTH TUA
                  LEFT JOIN TB_AUTH TA on TUA.AUTH_SEQ = TA.AUTH_SEQ
         WHERE TA.AUTH_DEPTH = 2
         GROUP BY AUTH_SEQ
     ) BTA
     ON TA.AUTH_SEQ = BTA.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
ORDER BY TA.AUTH_SEQ ASC
;











select AUTH_SEQ, AUTH_NAME
FROM TB_AUTH
WHERE UPPER_AUTH_SEQ = 22;


SELECT AUTH_SEQ
FROM TB_USER_AUTH
WHERE AUTH_SEQ = 22


SELECT COUNT(*) AS ACCOUNT_COUNT
FROM TB_USER_AUTH
WHERE AUTH_SEQ IN (
    95,96,97,98
    )

SELECT COUNT(*) AS COUNT
FROM TB_AUTH
WHERE AUTH_DEPTH IN (2,3)


SELECT COUNT(*) AS COUNT
FROM TB_USER





# TEST
-- 2뎁스 그룹의 하위 그룹의 수
SELECT
       TA.AUTH_NAME
        , IFNULL(SUM(TWO_ACCOUNT.AUTH_COUNT), 0) as TWO_ACCOUNT_COUNT
        , IFNULL(BTA.GROUP_COUNT, 0) AS COUNT
#         , COUNT(TWO_ACCOUNT.AUTH_COUNT) AS TWO_ACCOUNT_COUNT
        , IFNULL(BTA.THREE_ACCOUNT_COUNT, 0)
FROM TB_AUTH TA
LEFT JOIN
     (
         SELECT
                AA.UPPER_AUTH_SEQ
              , COUNT(AA.UPPER_AUTH_SEQ) AS GROUP_COUNT
              , SUM(TTT.AUTH_COUNT) as THREE_ACCOUNT_COUNT
         FROM TB_AUTH AA
         LEFT JOIN (
#              그룹별 모든 사용자
             SELECT TUA.AUTH_SEQ, COUNT(TUA.AUTH_SEQ) AS AUTH_COUNT
             FROM TB_USER_AUTH TUA
#              where AUTH_SEQ = 136 or AUTH_SEQ = 135
             GROUP BY TUA.AUTH_SEQ
         ) TTT
         ON TTT.AUTH_SEQ = AA.AUTH_SEQ
         WHERE AA.AUTH_DEPTH = 3
#            and (AA.AUTH_SEQ = 136 or AA.AUTH_SEQ = 135)
         GROUP BY AA.UPPER_AUTH_SEQ
     ) BTA
ON TA.AUTH_SEQ = BTA.UPPER_AUTH_SEQ
 LEFT JOIN (
#   그룹별 모든 사용자
    SELECT TUA.AUTH_SEQ, COUNT(TUA.AUTH_SEQ) AS AUTH_COUNT
    FROM TB_USER_AUTH TUA
    GROUP BY TUA.AUTH_SEQ
) TWO_ACCOUNT
ON TWO_ACCOUNT.AUTH_SEQ = TA.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
GROUP BY TA.AUTH_SEQ
ORDER BY TA.AUTH_SEQ ASC
;


