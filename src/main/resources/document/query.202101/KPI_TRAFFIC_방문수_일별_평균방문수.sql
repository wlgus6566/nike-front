-- 일별 평균 방문수
    SELECT USER.DATE
         , SUM(USER.COUNT) AS SUM
         , COUNT(USER.DATE) AS COUNT
         , ROUND(AVG(USER.COUNT), 0) AS AVERAGE
    FROM (
             SELECT DATE_FORMAT(TULL.LOGIN_DT, '2021') AS DATE, COUNT(*) AS COUNT
             FROM TB_USER_LOGIN_LOG TULL
#              WHERE LOGIN_DT BETWEEN '2020-12-16 00:00:00' AND '2021-01-15 23:59:59'
#              WHERE LOGIN_DT BETWEEN '2021-01-16 00:00:00' AND '2021-02-15 23:59:59'
             WHERE LOGIN_DT BETWEEN '2021-02-16 00:00:00' AND '2021-03-15 23:59:59'
# 	        AND TULL.DEVICE = 'PC'
# 	        AND TULL.DEVICE = 'MO'
             GROUP BY DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d")
         ) USER
    GROUP BY USER.DATE
;