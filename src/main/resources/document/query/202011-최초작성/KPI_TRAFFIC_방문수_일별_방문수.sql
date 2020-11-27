-- 일별 방문수 
SELECT DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d") AS DATE, COUNT(*) AS COUNT
FROM TB_USER_LOGIN_LOG TULL
WHERE TULL.LOGIN_DT BETWEEN '2020-01-01 00:00:00' AND '2020-12-31 23:59:59'
	AND TULL.DEVICE = 'PC'
GROUP BY DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d")
;