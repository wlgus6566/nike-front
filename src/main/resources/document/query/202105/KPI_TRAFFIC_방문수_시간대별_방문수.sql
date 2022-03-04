-- 접속 시간 대 별 방문수 
SELECT RESULT.TIME, COUNT(RESULT.TIME) AS COUNT
FROM (
         SELECT LOGIN_DT
              , CASE
                    WHEN LOGIN_DT BETWEEN CONCAT(DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'),
                                                 ' 00:00:00') AND CONCAT(
                            DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'), ' 02:59:59') THEN '00-03'
                    WHEN LOGIN_DT BETWEEN CONCAT(DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'),
                                                 ' 03:00:00') AND CONCAT(
                            DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'), ' 05:59:59') THEN '03-06'
                    WHEN LOGIN_DT BETWEEN CONCAT(DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'),
                                                 ' 06:00:00') AND CONCAT(
                            DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'), ' 08:59:59') THEN '06-09'
                    WHEN LOGIN_DT BETWEEN CONCAT(DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'),
                                                 ' 09:00:00') AND CONCAT(
                            DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'), ' 11:59:59') THEN '09-12'
                    WHEN LOGIN_DT BETWEEN CONCAT(DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'),
                                                 ' 12:00:00') AND CONCAT(
                            DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'), ' 14:59:59') THEN '12-15'
                    WHEN LOGIN_DT BETWEEN CONCAT(DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'),
                                                 ' 15:00:00') AND CONCAT(
                            DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'), ' 17:59:59') THEN '15-18'
                    WHEN LOGIN_DT BETWEEN CONCAT(DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'),
                                                 ' 18:00:00') AND CONCAT(
                            DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'), ' 20:59:59') THEN '18-21'
                    WHEN LOGIN_DT BETWEEN CONCAT(DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'),
                                                 ' 21:00:00') AND CONCAT(
                            DATE_FORMAT(LOGIN_DT, '%Y-%m-%d'), ' 23:59:59') THEN '21-24'
                    ELSE 'ETC'
             END AS TIME
         FROM TB_USER_LOGIN_LOG TULL
         WHERE TULL.LOGIN_DT BETWEEN '2021-10-16 00:00:00' AND '2021-11-15 23:59:59'
           -- ZABBIX 로그인에 사용한 계정 제외
           AND TULL.USER_SEQ != 2
     ) RESULT
GROUP BY RESULT.TIME
;