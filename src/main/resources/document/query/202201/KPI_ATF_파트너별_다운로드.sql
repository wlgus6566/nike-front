-- 다운로드 수

# 다운로드 테이블 (배열로 요청하면 하나하나 쌓임)
SELECT TA.AUTH_NAME, IFNULL(S.COUNT, 0) AS COUNT
FROM TB_AUTH TA
LEFT JOIN
(
    SELECT T.AUTH_SEQ, COUNT(T.AUTH_SEQ) AS COUNT
    FROM (
        SELECT 	CASE
            WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
            WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
            WHEN TA.AUTH_DEPTH = 3 THEN UTA.AUTH_SEQ
        END AS AUTH_SEQ, TA.AUTH_DEPTH
        FROM TB_DOWNLOAD_LOG TDL
        INNER JOIN TB_USER TU
        ON TDL.REGISTER_SEQ = TU.USER_SEQ
        INNER JOIN TB_USER_AUTH TUA
        ON TU.USER_SEQ = TUA.USER_SEQ
        INNER JOIN TB_AUTH TA
        ON TUA.AUTH_SEQ = TA.AUTH_SEQ
        LEFT JOIN TB_AUTH UTA
        ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
        LEFT JOIN TB_CONTENTS_FILE TCF
        ON TDL.FILE_SEQ = TCF.CONTENTS_FILE_SEQ
        LEFT JOIN TB_CONTENTS TC
        ON TCF.CONTENTS_SEQ = TC.CONTENTS_SEQ
        WHERE TDL.DOWNLOAD_TYPE = 'CONTENTS'
#          AND TC.TOP_MENU_CODE = 'ASSET'
#          AND TC.TOP_MENU_CODE = 'TOOLKIT'
         AND TC.TOP_MENU_CODE = 'FOUNDATION'
        AND TDL.REGISTRATION_DT BETWEEN '2022-01-16 00:00:00' AND '2022-02-15 23:59:59'
    ) T
    GROUP BY T.AUTH_SEQ
) S
ON TA.AUTH_SEQ = S.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2
ORDER BY TA.AUTH_SEQ;



# 액션로그 테이블 요청마다 1개로 ㅅ쌓임
-- ATF_그룹별_다운로드수
# SELECT TA.AUTH_NAME, IFNULL(S.COUNT, 0) AS COUNT
# FROM TB_AUTH TA
#          LEFT JOIN
#      (
#      SELECT T.AUTH_SEQ, COUNT(T.AUTH_SEQ) AS COUNT
#      FROM (
#         SELECT CASE
#             WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
#             WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
#             WHEN TA.AUTH_DEPTH = 3 THEN UTA.AUTH_SEQ
#             END AS AUTH_SEQ
#         FROM TB_USER_ACTION_LOG TDL
#         INNER JOIN TB_USER TU
#         ON TDL.REGISTER_SEQ = TU.USER_SEQ
#         INNER JOIN TB_USER_AUTH TUA
#         ON TU.USER_SEQ = TUA.USER_SEQ
#         INNER JOIN TB_AUTH TA
#         ON TUA.AUTH_SEQ = TA.AUTH_SEQ
#         LEFT JOIN TB_AUTH UTA
#         ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
#         WHERE TDL.PARAMETER like '%downloadType=CONTENTS%'
#         AND TDL.REGISTRATION_DT BETWEEN '2020-12-16 00:00:00' AND '2021-01-15 23:59:59'
# ) T
# GROUP BY T.AUTH_SEQ
#  ) S
#  ON TA.AUTH_SEQ = S.AUTH_SEQ
# # WHERE TA.AUTH_DEPTH = 1
# WHERE TA.AUTH_DEPTH = 2
# ORDER BY TA.AUTH_SEQ;

# 10월 : 09.16 ~ 10.15
# 11월 : 10.16 ~ 11.15
# 12월 : 11.16 ~ 12.15



# SELECT TDL.PARAMETER, TDL.REGISTRATION_DT
# FROM TB_USER_ACTION_LOG TDL
# WHERE TDL.PARAMETER like '%downloadType=CONTENTS%'
# #   AND TDL.REGISTRATION_DT BETWEEN '2020-10-26 17:04:53' AND '2020-11-30 23:59:59'
# ORDER BY TDL.REGISTRATION_DT ASC

select * from TB_DOWNLOAD_LOG;