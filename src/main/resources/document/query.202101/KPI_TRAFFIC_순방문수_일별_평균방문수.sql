-- 일별 평균 방문수
SELECT T.DATE
     , SUM(T.COUNT) AS SUM
     , COUNT(T.DATE) AS COUNT
     , ROUND(AVG(T.COUNT), 0) AS AVERAGE
FROM (
         SELECT DATE_FORMAT(USER.DATE, '2021') AS DATE
              , COUNT(USER.DATE) AS COUNT
         FROM (
                SELECT DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d") AS DATE
                FROM TB_USER_LOGIN_LOG TULL
#                 WHERE TULL.LOGIN_DT BETWEEN '2020-12-16 00:00:00' AND '2021-01-15 23:59:59'
                WHERE TULL.LOGIN_DT BETWEEN '2021-01-16 00:00:00' AND '2021-02-15 23:59:59'
#                 AND TULL.DEVICE = 'PC'
#                 AND TULL.DEVICE = 'MO'
                GROUP BY DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d"), TULL.USER_SEQ
              ) USER
         GROUP BY USER.DATE
     ) T
GROUP BY T.DATE