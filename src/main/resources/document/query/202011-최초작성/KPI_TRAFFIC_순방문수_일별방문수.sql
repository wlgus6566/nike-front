-- 일별 순 방문수 
SELECT USER.DATE, COUNT(USER.DATE) AS COUNT
FROM (
	SELECT DATE_FORMAT(LOGIN_DT, "%Y-%m-%d") AS DATE
	FROM TB_USER_LOGIN_LOG TULL
	WHERE TULL.LOGIN_DT BETWEEN '2020-01-01 00:00:00' AND '2020-11-12 23:59:59'
	GROUP BY DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d"), TULL.USER_SEQ
) USER
GROUP BY USER.DATE
;