-- 2,3DEPTH 그룹 수 / 계정 수
SELECT -- TA.AUTH_SEQ,
		TA.AUTH_NAME,
       IFNULL(TWO_ACC.TOTAL_ACC, 0)   AS 'TWO_TOTAL_ACC',
       IFNULL(TWO_ACC.NORMAL_ACC, 0)   AS 'TWO_NORMAL_ACC',
       IFNULL(TWO_ACC.DORMANT_ACC, 0)   AS 'TWO_DORMANT_ACC',
       IFNULL(TWO_ACC.DELETE_ACC, 0)   AS 'TWO_DELETE_ACC',
       IFNULL(TRE_GRU.COUNT, 0) AS 'TRE_GROUP',
       IFNULL(TRE_ACC.TOTAL_ACC, 0)   AS 'TRE_TOTAL_ACC',
       IFNULL(TRE_ACC.NORMAL_ACC, 0)   AS 'TRE_NORMAL_ACC',
       IFNULL(TRE_ACC.DORMANT_ACC, 0)   AS 'TRE_DORMANT_ACC',
       IFNULL(TRE_ACC.DELETE_ACC, 0)   AS 'TRE_DELETE_ACC'
       
FROM TB_AUTH TA

-- 2DEPTH_계정_수
LEFT JOIN (
	
	SELECT
		TU.AUTH_SEQ
		, SUM(TU.COUNT) AS TOTAL_ACC
		, MAX(IF(TU.USER_STATUS_CODE = 'NORMAL', TU.COUNT, 0)) AS NORMAL_ACC
		, MAX(IF(TU.USER_STATUS_CODE = 'DORMANT', TU.COUNT, 0)) AS DORMANT_ACC
		, MAX(IF(TU.USER_STATUS_CODE = 'DELETE', TU.COUNT, 0)) AS DELETE_ACC
	FROM
	(
	    SELECT TA.AUTH_SEQ, TU.USER_STATUS_CODE, COUNT(TA.AUTH_SEQ) AS COUNT
	    FROM TB_USER_AUTH TUA
	             LEFT JOIN TB_AUTH TA on TUA.AUTH_SEQ = TA.AUTH_SEQ
	             LEFT JOIN TB_USER TU on TUA.USER_SEQ = TU.USER_SEQ
	    WHERE TA.AUTH_DEPTH = 2
	--      AND TU.USER_STATUS_CODE = 'NORMAL'
	      AND TU.REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2022-01-15 23:59:59'
	    GROUP BY AUTH_SEQ, TU.USER_STATUS_CODE
	
	) TU
	GROUP BY TU.AUTH_SEQ

/*
    SELECT TA.AUTH_SEQ, TU.USER_STATUS_CODE, COUNT(TA.AUTH_SEQ) AS COUNT
    FROM TB_USER_AUTH TUA
             LEFT JOIN TB_AUTH TA on TUA.AUTH_SEQ = TA.AUTH_SEQ
             LEFT JOIN TB_USER TU on TUA.USER_SEQ = TU.USER_SEQ
    WHERE TA.AUTH_DEPTH = 2
--      AND TU.USER_STATUS_CODE = 'NORMAL'
      AND TU.REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2021-12-15 23:59:59'
    GROUP BY AUTH_SEQ, TU.USER_STATUS_CODE
*/
 ) TWO_ACC ON TA.AUTH_SEQ = TWO_ACC.AUTH_SEQ
-- 3DEPTH_계정_수
LEFT OUTER JOIN (
	SELECT
		TU.UPPER_AUTH_SEQ
		, SUM(TU.COUNT) AS TOTAL_ACC
		, MAX(IF(TU.USER_STATUS_CODE = 'NORMAL', TU.COUNT, 0)) AS NORMAL_ACC
		, MAX(IF(TU.USER_STATUS_CODE = 'DORMANT', TU.COUNT, 0)) AS DORMANT_ACC
		, MAX(IF(TU.USER_STATUS_CODE = 'DELETE', TU.COUNT, 0)) AS DELETE_ACC
	FROM
	(
	     SELECT 
			  	TA.UPPER_AUTH_SEQ
				, TU.USER_STATUS_CODE
			  , COUNT(TA.UPPER_AUTH_SEQ) AS COUNT
	    FROM TB_USER_AUTH TUA
	             LEFT JOIN TB_AUTH TA on TUA.AUTH_SEQ = TA.AUTH_SEQ
	             LEFT JOIN TB_USER TU on TUA.USER_SEQ = TU.USER_SEQ
	    WHERE TA.AUTH_DEPTH = 3
	      AND TU.REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2022-01-15 23:59:59'
	    GROUP BY UPPER_AUTH_SEQ, TU.USER_STATUS_CODE
	) TU
	GROUP BY TU.UPPER_AUTH_SEQ


/*
    SELECT TA.UPPER_AUTH_SEQ, TU.USER_STATUS_CODE, COUNT(TA.UPPER_AUTH_SEQ) AS COUNT
    FROM TB_USER_AUTH TUA
             LEFT JOIN TB_AUTH TA on TUA.AUTH_SEQ = TA.AUTH_SEQ
             LEFT JOIN TB_USER TU on TUA.USER_SEQ = TU.USER_SEQ
    WHERE TA.AUTH_DEPTH = 3
--       AND TU.USER_STATUS_CODE = 'NORMAL'
      AND TU.REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2021-12-15 23:59:59'
    GROUP BY UPPER_AUTH_SEQ, TU.USER_STATUS_CODE
*/
    ) TRE_ACC ON TA.AUTH_SEQ = TRE_ACC.UPPER_AUTH_SEQ

-- 3DEPTH_그룹_수
 LEFT OUTER JOIN (
    SELECT UPPER_AUTH_SEQ, COUNT(UPPER_AUTH_SEQ) AS COUNT
    FROM TB_AUTH
    WHERE AUTH_DEPTH = 3
      AND USE_YN = 'Y'
      AND REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2022-01-15 23:59:59'
    GROUP BY UPPER_AUTH_SEQ
) TRE_GRU ON TA.AUTH_SEQ = TRE_GRU.UPPER_AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
   AND TA.USE_YN = 'Y'
  AND TA.REGISTRATION_DT BETWEEN '2020-08-01 00:00:00' AND '2022-01-15 23:59:59'
ORDER BY TA.AUTH_SEQ ASC
;
