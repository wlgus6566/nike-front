-- 일별 평균 방문수
SELECT T.DATE
     , SUM(T.COUNT) AS SUM
     , COUNT(T.DATE) AS COUNT
     , ROUND(AVG(T.COUNT), 0) AS AVERAGE
FROM (
         SELECT DATE_FORMAT(USER.DATE, '2022') AS DATE
              , COUNT(USER.DATE) AS COUNT
         FROM (
                SELECT DATE_FORMAT(TULL.LOGIN_DT, '%Y-%m-%d') AS DATE
                FROM TB_USER_LOGIN_LOG TULL
                WHERE TULL.LOGIN_DT BETWEEN '2022-02-16 00:00:00' AND '2022-03-15 23:59:59'
                -- ZABBIX 로그인에 사용한 계정 제외
                AND TULL.USER_SEQ != 2
                GROUP BY DATE_FORMAT(TULL.LOGIN_DT, '%Y-%m-%d'), TULL.USER_SEQ
              ) USER
         GROUP BY USER.DATE
     ) T
GROUP BY T.DATE
;