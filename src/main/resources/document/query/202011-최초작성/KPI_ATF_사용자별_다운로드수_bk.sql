-- 유저별 다운로드 수
SELECT TU.USER_ID
     , TU.NICKNAME
     , TA.AUTH_NAME
     , ACTION.COUNT
FROM (
    SELECT tual.USER_SEQ, COUNT(tual.USER_SEQ) AS COUNT
    FROM TB_USER_ACTION_LOG tual
    WHERE tual.METHOD_SIGNATURE = 'downloadLog'
      AND tual.PARAMETER like '%downloadType=CONTENTS%'
    AND (tual.REGISTRATION_DT BETWEEN '2020-01-01 00:00:00' AND '2020-11-30 23:59:59')
#     AND tual.device = 'PC'
    # AND tual.device = 'MO'
    GROUP BY tual.USER_SEQ
) ACTION
INNER JOIN TB_USER TU
ON ACTION.USER_SEQ = TU.USER_SEQ
INNER JOIN TB_USER_AUTH TUA
ON TU.USER_SEQ = TUA.USER_SEQ
INNER JOIN TB_AUTH TA
ON TUA.AUTH_SEQ = TA.AUTH_SEQ
ORDER BY TU.USER_SEQ ASC
;


# SELECT *
# FROM TB_USER_ACTION_LOG tual
# WHERE tual.METHOD_SIGNATURE = 'downloadLog'
# ORDEr BY 1 ASC
-- 2020-10-26 17:10:43 ~ 2020-11-10 09:43:59