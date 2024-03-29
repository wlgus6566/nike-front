-- A/T/F 게시물별 다운로드 수
# 안됨!!
# SELECT TC.CONTENTS_SEQ, TC.FOLDER_NAME
#      , IFNULL(SUM(D.ELE), 0) AS ELE
#      , IFNULL(SUM(D.TWO), 0) AS TWO
# FROM TB_CONTENTS TC
# LEFT JOIN (
#     SELECT
# #            TDL.FILE_SEQ
#     CASE WHEN TDL.REGISTRATION_DT BETWEEN '2020-11-13 11:55:33' AND '2020-11-30 23:59:59' THEN COUNT(TDL.DOWNLOAD_LOG_SEQ) END AS ELE
# #     , CASE WHEN TDL.REGISTRATION_DT BETWEEN '2020-12-01 00:00:00' AND '2020-12-31 23:59:59' THEN COUNT(TDL.FILE_SEQ) END AS TWO
# #     , TCF.CONTENTS_SEQ
#     FROM TB_DOWNLOAD_LOG TDL
#     INNER JOIN TB_CONTENTS_FILE TCF
#     ON TDL.FILE_SEQ = TCF.CONTENTS_FILE_SEQ
#     WHERE TDL.DOWNLOAD_TYPE = 'CONTENTS'
#     GROUP BY TDL.FILE_SEQ
# ) D
# ON TC.CONTENTS_SEQ = D.CONTENTS_SEQ
# GROUP BY TC.CONTENTS_SEQ





# 되는쿼리
-- ASSET 게시물별 다운로드 수
-- TOOLKIT 게시물별 다운로드 수
-- FOUNDATION 게시물별 다운로드 수
SELECT tc.CONTENTS_SEQ, tc.FOLDER_NAME
      , SUM(CASE
                WHEN T.REGISTRATION_DT BETWEEN '2020-11-13 11:35:00' AND '2020-11-15 23:59:59' THEN 1
                WHEN T.REGISTRATION_DT BETWEEN '2020-11-16 00:00:00' AND '2020-12-15 23:59:59' THEN 0
                ELSE 0
        END) 'ELE'
      , SUM(CASE
                WHEN T.REGISTRATION_DT BETWEEN '2020-11-13 11:35:00' AND '2020-11-15 23:59:59' THEN 0
                WHEN T.REGISTRATION_DT BETWEEN '2020-11-16 00:00:00' AND '2020-12-15 23:59:59' THEN 1
                ELSE 0
        END) 'TWO'
 FROM TB_CONTENTS tc
LEFT JOIN (
  SELECT tcf.CONTENTS_SEQ, tdl.REGISTRATION_DT
  FROM TB_DOWNLOAD_LOG tdl
           INNER JOIN TB_CONTENTS_FILE tcf
                      ON tdl.FILE_SEQ = tcf.CONTENTS_FILE_SEQ
  WHERE tdl.REGISTRATION_DT BETWEEN '2020-11-13 11:35:00' AND '2020-12-15 23:59:59'
    AND tdl.DOWNLOAD_TYPE = 'CONTENTS'
) T
ON T.CONTENTS_SEQ = tc.CONTENTS_SEQ
# WHERE tc.TOP_MENU_CODE = 'ASSET'
# WHERE tc.TOP_MENU_CODE = 'TOOLKIT'
WHERE tc.TOP_MENU_CODE = 'FOUNDATION'
 GROUP BY tc.CONTENTS_SEQ
;






