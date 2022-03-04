-- 일별 평균 방문수
    SELECT USER.DATE
         , SUM(USER.COUNT) AS SUM
         , COUNT(USER.DATE) AS COUNT
         , ROUND(AVG(USER.COUNT), 0) AS AVERAGE
    FROM (
             SELECT DATE_FORMAT(TULL.LOGIN_DT, '2021') AS DATE, COUNT(*) AS COUNT
             FROM TB_USER_LOGIN_LOG TULL
             WHERE LOGIN_DT BETWEEN '2021-10-16 00:00:00' AND '2021-11-15 23:59:59'
               -- ZABBIX 로그인에 사용한 계정 제외
             AND TULL.USER_SEQ != 2
             GROUP BY DATE_FORMAT(TULL.LOGIN_DT, '%Y-%m-%d')
         ) USER
    GROUP BY USER.DATE
;