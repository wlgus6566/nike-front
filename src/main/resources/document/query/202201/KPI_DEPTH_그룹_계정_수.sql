-- 그룹수
-- 기간 : 최초 등록일 ~
SELECT AUTH_DEPTH, COUNT(AUTH_DEPTH)
FROM TB_AUTH
WHERE REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2025-12-29 23:59:59'
  AND USE_YN = 'Y'
GROUP BY AUTH_DEPTH;



-- 계정수
SELECT 
	TU.AUTH_DEPTH
	, SUM(TU.COUNT) AS TOTAL_ACC
	, MAX(IF(TU.USER_STATUS_CODE = 'NORMAL', TU.COUNT, 0)) AS NORMAL_ACC
	, MAX(IF(TU.USER_STATUS_CODE = 'DORMANT', TU.COUNT, 0)) AS DORMANT_ACC
	, MAX(IF(TU.USER_STATUS_CODE = 'DELETE', TU.COUNT, 0)) AS DELETE_ACC
FROM
(
	SELECT 
		TA.AUTH_DEPTH
		, TU.USER_STATUS_CODE
		, COUNT(TA.AUTH_DEPTH) AS COUNT
	FROM TB_USER TU
	LEFT JOIN TB_USER_AUTH TUA 	ON TU.USER_SEQ = TUA.USER_SEQ
	LEFT JOIN TB_AUTH TA 	on TA.AUTH_SEQ = TUA.AUTH_SEQ
	WHERE TU.REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2025-03-15 23:59:59'
		AND TA.USE_YN = 'Y'	
	-- AND TU.USER_STATUS_CODE = 'NORMAL'
	GROUP BY TA.AUTH_DEPTH, TU.USER_STATUS_CODE
) TU
GROUP BY TU.AUTH_DEPTH
;


-- 그룹수/계정수
SELECT TG.*, TA.TOTAL_ACC, TA.NORMAL_ACC, TA.DORMANT_ACC, TA.DELETE_ACC
FROM (
         SELECT AUTH_DEPTH, COUNT(AUTH_DEPTH) AS GROUP_COUNT
         FROM TB_AUTH
         WHERE REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2025-02-15 23:59:59'
           AND USE_YN = 'Y'
         GROUP BY AUTH_DEPTH
     ) TG
         LEFT OUTER JOIN (

			SELECT
				TU.AUTH_DEPTH
				, SUM(TU.ACCOUNT_COUNT) AS TOTAL_ACC
				, MAX(IF(TU.USER_STATUS_CODE = 'NORMAL', TU.ACCOUNT_COUNT, 0)) AS NORMAL_ACC
				, MAX(IF(TU.USER_STATUS_CODE = 'DORMANT', TU.ACCOUNT_COUNT, 0)) AS DORMANT_ACC
				, MAX(IF(TU.USER_STATUS_CODE = 'DELETE', TU.ACCOUNT_COUNT, 0)) AS DELETE_ACC
			FROM 
			(

			    SELECT TA.AUTH_DEPTH, TU.USER_STATUS_CODE, COUNT(TA.AUTH_SEQ) AS ACCOUNT_COUNT
			    FROM TB_USER_AUTH TUA
	             LEFT JOIN TB_AUTH TA on TUA.AUTH_SEQ = TA.AUTH_SEQ
	             LEFT JOIN TB_USER TU on TUA.USER_SEQ = TU.USER_SEQ

/*
			    SELECT TA.AUTH_DEPTH, TU.USER_STATUS_CODE, COUNT(TA.AUTH_DEPTH) AS ACCOUNT_COUNT
			    FROM TB_USER TU
	             LEFT JOIN TB_USER_AUTH TUA ON TU.USER_SEQ = TUA.USER_SEQ
	             LEFT JOIN TB_AUTH TA on TA.AUTH_SEQ = TUA.AUTH_SEQ
*/
			    WHERE TU.REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2025-03-15 23:59:59'
				   AND TA.USE_YN = 'Y'			    
			--      AND TU.USER_STATUS_CODE = 'NORMAL'
			    GROUP BY TA.AUTH_DEPTH, TU.USER_STATUS_CODE
			) TU
			GROUP BY TU.AUTH_DEPTH

/*
	    SELECT TA.AUTH_DEPTH, TU.USER_STATUS_CODE, COUNT(TA.AUTH_DEPTH) AS ACCOUNT_COUNT
	    FROM TB_USER TU
	             LEFT JOIN TB_USER_AUTH TUA
	                       ON TU.USER_SEQ = TUA.USER_SEQ
	             LEFT JOIN TB_AUTH TA
	                       on TA.AUTH_SEQ = TUA.AUTH_SEQ
	    WHERE TU.REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2021-12-15 23:59:59'
	--      AND TU.USER_STATUS_CODE = 'NORMAL'
	    GROUP BY TA.AUTH_DEPTH, TU.USER_STATUS_CODE
*/
) TA
ON TG.AUTH_DEPTH = TA.AUTH_DEPTH
;