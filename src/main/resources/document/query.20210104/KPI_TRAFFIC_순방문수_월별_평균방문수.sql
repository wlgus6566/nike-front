-- 월별 평균 순 방문수 
SELECT DATE_FORMAT(RESULT.DATE, "%Y") AS DATE
		, SUM(RESULT.COUNT) AS SUM
		, COUNT(RESULT.DATE) AS COUNT
		, ROUND(AVG(RESULT.COUNT), 0) AS AVERAGE
FROM (
	SELECT USER.DATE, COUNT(USER.DATE) AS COUNT
	FROM (
		SELECT DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d") AS DATE
		FROM TB_USER_LOGIN_LOG TULL
		WHERE TULL.LOGIN_DT BETWEEN '2020-09-24 00:00:00' AND '2020-11-30 23:59:59'
# 	AND TULL.DEVICE = 'PC'
		GROUP BY DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d"), TULL.USER_SEQ
	) USER
	GROUP BY USER.DATE
) RESULT
GROUP BY DATE_FORMAT(RESULT.DATE, "%Y")
;