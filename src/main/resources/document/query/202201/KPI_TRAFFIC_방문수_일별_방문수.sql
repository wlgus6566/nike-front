-- 일별 방문수 
SELECT DATE_FORMAT(TULL.LOGIN_DT, '%Y-%m-%d') AS DATE, COUNT(*) AS COUNT
FROM TB_USER_LOGIN_LOG TULL
WHERE TULL.LOGIN_DT BETWEEN '2022-03-16 00:00:00' AND '2022-04-15 23:59:59'
  -- ZABBIX 로그인에 사용한 계정 제외
AND TULL.USER_SEQ != 2
GROUP BY DATE_FORMAT(TULL.LOGIN_DT, '%Y-%m-%d')
;