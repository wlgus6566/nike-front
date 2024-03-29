-- 일별 평균 방문수
    SELECT USER.DATE
         , SUM(USER.COUNT) AS SUM
         , COUNT(USER.DATE) AS COUNT
         , ROUND(AVG(USER.COUNT), 0) AS AVERAGE
    FROM (
             SELECT DATE_FORMAT(TULL.LOGIN_DT, "%Y") AS DATE, COUNT(*) AS COUNT
             FROM TB_USER_LOGIN_LOG TULL
#              WHERE LOGIN_DT BETWEEN '2020-09-24 00:00:00' AND '2020-10-15 23:59:59'
#              WHERE LOGIN_DT BETWEEN '2020-10-16 00:00:00' AND '2020-11-15 23:59:59'
             WHERE LOGIN_DT BETWEEN '2020-11-16 00:00:00' AND '2020-12-15 23:59:59'
# 	AND TULL.DEVICE = 'PC'
             GROUP BY DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d")
         ) USER
    GROUP BY USER.DATE
;