-- 일별 순 방문수 
SELECT USER.DATE, COUNT(USER.DATE) AS COUNT
FROM (
	SELECT DATE_FORMAT(LOGIN_DT, '%Y-%m-%d') AS DATE
	FROM TB_USER_LOGIN_LOG TULL
	WHERE TULL.LOGIN_DT BETWEEN '2021-10-16 00:00:00' AND '2021-11-15 23:59:59'
      -- ZABBIX 로그인에 사용한 계정 제외
	AND TULL.USER_SEQ != 2
	GROUP BY DATE_FORMAT(TULL.LOGIN_DT, '%Y-%m-%d'), TULL.USER_SEQ
) USER
GROUP BY USER.DATE
;