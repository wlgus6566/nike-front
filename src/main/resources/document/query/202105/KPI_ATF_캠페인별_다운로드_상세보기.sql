# -- ASSET/TOOLKIT/FOUNDATION 다운로드상세보기

-- A/T/F별 상세보기 수
-- 기간 내에 조회
SELECT
       TA.CATEGORY,  IFNULL(COUNT(TA.CATEGORY), 0) AS cnt
FROM (
        SELECT CASE
        # ASSET
        WHEN URL LIKE '%ASSET%' AND URL LIKE '%SP%' THEN '1_ASSET_SP'
        WHEN URL LIKE '%ASSET%' AND URL LIKE '%SU%' THEN '2_ASSET_SU'
        WHEN URL LIKE '%ASSET%' AND URL LIKE '%FA%' THEN '3_ASSET_FA'
        WHEN URL LIKE '%ASSET%' AND URL LIKE '%HO%' THEN '4_ASSET_HO'

        # TOOLKIT
#         WHEN URL LIKE '%TOOLKIT%' AND URL LIKE '%VMS%' THEN '1_TOOLKIT_VMS'
#         WHEN URL LIKE '%TOOLKIT%' AND URL LIKE '%EKIN%' THEN '2_TOOLKIT_EKIN'
#         WHEN URL LIKE '%TOOLKIT%' AND URL LIKE '%SOCIAL%' THEN '3_TOOLKIT_SOCIAL'
#         WHEN URL LIKE '%TOOLKIT%' AND URL LIKE '%RB%' THEN '4_TOOLKIT_RB'

        # FOUNDATION
#         WHEN URL LIKE '%FOUNDATION%' AND URL LIKE '%VMS%' THEN '1_FOUNDATION_VMS'
#         WHEN URL LIKE '%FOUNDATION%' AND URL LIKE '%EKIN%' THEN '2_FOUNDATION_EKIN'
#         WHEN URL LIKE '%FOUNDATION%' AND URL LIKE '%DIGITAL%' THEN '3_FOUNDATION_DIGITAL'
#         WHEN URL LIKE '%FOUNDATION%' AND URL LIKE '%RB%' THEN '4_FOUNDATION_RB'
        END AS CATEGORY

        FROM TB_USER_ACTION_LOG
        WHERE METHOD_SIGNATURE = 'findContents'
            AND METHOD_TYPE_NAME = 'GET'
            AND REGISTRATION_DT BETWEEN '2021-04-16 00:00:00' AND '2021-05-15 23:59:59'
    ) TA
WHERE TA.CATEGORY IS NOT NULL
GROUP BY TA.CATEGORY
ORDER BY CATEGORY
;

-- A/T/F별 업로드 수
SELECT
    T.MENU_CODE, COUNT(T.MENU_CODE) AS COUNT
FROM
    (
        SELECT CASE
                   WHEN TOP_MENU_CODE = 'ASSET' AND MENU_CODE = 'SP' THEN 'ASSET_SP'
                   WHEN TOP_MENU_CODE = 'ASSET' AND MENU_CODE = 'SU' THEN 'ASSET_SU'
                   WHEN TOP_MENU_CODE = 'ASSET' AND MENU_CODE = 'FA' THEN 'ASSET_FA'
                   WHEN TOP_MENU_CODE = 'ASSET' AND MENU_CODE = 'HO' THEN 'ASSET_HO'

                   WHEN TOP_MENU_CODE = 'TOOLKIT' AND MENU_CODE = 'VMS' THEN 'TOOLKIT_VMS'
                   WHEN TOP_MENU_CODE = 'TOOLKIT' AND MENU_CODE = 'EKIN' THEN 'TOOLKIT_EKIN'
                   WHEN TOP_MENU_CODE = 'TOOLKIT' AND MENU_CODE = 'SOCIAL' THEN 'TOOLKIT_SOCIAL'
                   WHEN TOP_MENU_CODE = 'TOOLKIT' AND MENU_CODE = 'RB' THEN 'TOOLKIT_RB'

                   WHEN TOP_MENU_CODE = 'FOUNDATION' AND MENU_CODE = 'VMS' THEN 'FOUNDATION_VMS'
                   WHEN TOP_MENU_CODE = 'FOUNDATION' AND MENU_CODE = 'EKIN' THEN 'FOUNDATION_EKIN'
                   WHEN TOP_MENU_CODE = 'FOUNDATION' AND MENU_CODE = 'DIGITAL' THEN 'FOUNDATION_DIGITAL'
                   WHEN TOP_MENU_CODE = 'FOUNDATION' AND MENU_CODE = 'RB' THEN 'FOUNDATION_RB'

                   END AS MENU_CODE
        FROM TB_CONTENTS
        WHERE REGISTRATION_DT BETWEEN '2021-04-16 00:00:00' AND '2021-05-15 23:59:59'
    ) T
GROUP BY T.MENU_CODE;
;



# ASSET/TOOLKIT/FOUNDATION 게시물 다운로드 상세보기 수
-- 기간내에 조회 & 기간내에 업로드한 게시물
SELECT CASE
    WHEN TC.TOP_MENU_CODE = 'ASSET' AND TC.MENU_CODE = 'SP' THEN '1_1_ASSET_SP'
    WHEN TC.TOP_MENU_CODE = 'ASSET' AND TC.MENU_CODE = 'SU' THEN '1_2_ASSET_SU'
    WHEN TC.TOP_MENU_CODE = 'ASSET' AND TC.MENU_CODE = 'FA' THEN '1_3_ASSET_FA'
    WHEN TC.TOP_MENU_CODE = 'ASSET' AND TC.MENU_CODE = 'HO' THEN '1_4_ASSET_HO'

    WHEN TC.TOP_MENU_CODE = 'TOOLKIT' AND TC.MENU_CODE = 'VMS' THEN '2_1_TOOLKIT_VMS'
    WHEN TC.TOP_MENU_CODE = 'TOOLKIT' AND TC.MENU_CODE = 'EKIN' THEN '2_2_TOOLKIT_EKIN'
    WHEN TC.TOP_MENU_CODE = 'TOOLKIT' AND TC.MENU_CODE = 'SOCIAL' THEN '2_3_TOOLKIT_SOCIAL'
    WHEN TC.TOP_MENU_CODE = 'TOOLKIT' AND TC.MENU_CODE = 'RB' THEN '2_4_TOOLKIT_RB'

    WHEN TC.TOP_MENU_CODE = 'FOUNDATION' AND TC.MENU_CODE = 'VMS' THEN '3_1_FOUNDATION_VMS'
    WHEN TC.TOP_MENU_CODE = 'FOUNDATION' AND TC.MENU_CODE = 'EKIN' THEN '3_2_FOUNDATION_EKIN'
    WHEN TC.TOP_MENU_CODE = 'FOUNDATION' AND TC.MENU_CODE = 'DIGITAL' THEN '3_3_FOUNDATION_DIGITAL'
    WHEN TC.TOP_MENU_CODE = 'FOUNDATION' AND TC.MENU_CODE = 'RB' THEN '3_4_FOUNDATION_RB'

    END AS MENU_CA_CODE,
    TC.FOLDER_NAME,
    DATE_FORMAT(TC.REGISTRATION_DT, '%Y-%m-%d') as DT,
    IFNULL(TT.FIND_COUNT, 0) AS FIND_COUNT,
    IFNULL(TT.DOWN_COUNT , 0) AS DOWN_COUNT
FROM TB_CONTENTS TC
LEFT JOIN (
    SELECT
        TF.CONTENTS_SEQ,
        SUM(TF.FIND_COUNT) AS FIND_COUNT,
        SUM(TD.DOWN_COUNT) AS DOWN_COUNT
    FROM (
        SELECT TT.CONTENTS_SEQ, COUNT(TT.CONTENTS_SEQ) AS FIND_COUNT
        FROM (
            SELECT SUBSTRING_INDEX(URL, '/', -1) AS CONTENTS_SEQ
            FROM TB_USER_ACTION_LOG
            WHERE METHOD_SIGNATURE = 'findContents'
            AND METHOD_TYPE_NAME = 'GET'
            AND REGISTRATION_DT BETWEEN '2021-04-16 00:00:00' AND '2021-05-15 23:59:59'
        ) TT
        GROUP BY TT.CONTENTS_SEQ
    ) TF
LEFT JOIN (
    SELECT
           TCF.CONTENTS_SEQ,
           COUNT(TCF.CONTENTS_SEQ) AS DOWN_COUNT
    FROM TB_DOWNLOAD_LOG TDL
    INNER JOIN TB_CONTENTS_FILE TCF
    ON TDL.FILE_SEQ = TCF.CONTENTS_FILE_SEQ
    WHERE TDL.DOWNLOAD_TYPE = 'CONTENTS'
        AND TDL.REGISTRATION_DT BETWEEN '2021-04-16 00:00:00' AND '2021-05-15 23:59:59'
    GROUP BY TCF.CONTENTS_SEQ
) TD
ON TF.CONTENTS_SEQ = TD.CONTENTS_SEQ
GROUP BY TF.CONTENTS_SEQ
) TT
ON TC.CONTENTS_SEQ = TT.CONTENTS_SEQ
WHERE TC.REGISTRATION_DT BETWEEN '2021-04-16 00:00:00' AND '2021-05-15 23:59:59'
GROUP BY TC.CONTENTS_SEQ
ORDER BY MENU_CA_CODE, TC.CONTENTS_SEQ
;
