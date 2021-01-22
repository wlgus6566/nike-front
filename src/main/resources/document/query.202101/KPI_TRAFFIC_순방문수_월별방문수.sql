-- 월별 순 방문수 
SELECT USER.DATE, COUNT(USER.DATE) AS COUNT
FROM (
         SELECT DATE_FORMAT(TULL.LOGIN_DT, '2021') AS DATE, TULL.DEVICE
         FROM TB_USER_LOGIN_LOG TULL
         WHERE TULL.LOGIN_DT BETWEEN '2020-12-16 00:00:00' AND '2021-01-15 23:59:59'
         AND TULL.DEVICE = 'PC'
#          AND TULL.DEVICE = 'MO'
#          AND TULL.DEVICE IS NULL
         GROUP BY DATE_FORMAT(TULL.LOGIN_DT, "%Y-%m-%d"), TULL.USER_SEQ
     ) USER
GROUP BY USER.DATE
;