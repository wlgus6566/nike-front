-- 그룹 권한 별 리포트 업로드 수

-- REPORT
-- REPORT 업로드 수
-- 계정 수 추가
SELECT
    CASE
        WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_NAME
        WHEN TA.AUTH_DEPTH = 3 THEN ''
        END AS 2DEPTH_NAME,
    CASE
        WHEN TA.AUTH_DEPTH = 2 THEN ''
        WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_NAME
        END AS 3DEPTH_NAME,
    IFNULL(S.COUNT, 0) AS COUNT,
    IFNULL(S.SEQ, 0) AS ACCOUNT_COUNT,
    CASE
        WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
        WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
        END AS UPPER_AUTH_SEQ
FROM TB_AUTH TA
LEFT JOIN
(
    SELECT
        B.AUTH_SEQ,
        SUM(B.COUNT) AS COUNT,
        COUNT(B.REGISTER_SEQ) AS SEQ,
        B.AUTH_DEPTH,
        B.UPPER_AUTH_SEQ
    FROM (
        SELECT T.AUTH_SEQ,
            COUNT(T.AUTH_SEQ) AS COUNT,
            T.REGISTER_SEQ,
            T.AUTH_DEPTH,
            T.UPPER_AUTH_SEQ
        FROM (
            SELECT CASE
                WHEN TA.AUTH_DEPTH = 1 THEN TA.AUTH_SEQ
                WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                WHEN TA.AUTH_DEPTH = 3 THEN TA.AUTH_SEQ
                END AS AUTH_SEQ,
                TA.AUTH_DEPTH,
                CASE
                WHEN TA.AUTH_DEPTH = 2 THEN TA.AUTH_SEQ
                WHEN TA.AUTH_DEPTH = 3 THEN TA.UPPER_AUTH_SEQ
                END AS UPPER_AUTH_SEQ,
                TR.REGISTER_SEQ
            FROM TB_REPORT TR
            INNER JOIN TB_AUTH TA
            ON TR.AUTH_SEQ = TA.AUTH_SEQ
            LEFT JOIN TB_AUTH UTA
            ON TA.UPPER_AUTH_SEQ = UTA.AUTH_SEQ
            WHERE TR.REGISTRATION_DT BETWEEN '2022-03-16 00:00:00' AND '2022-04-15 23:59:59'
            AND (TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3)
        ) T
        GROUP BY T.AUTH_SEQ, T.REGISTER_SEQ
    ) B
    GROUP BY B.AUTH_SEQ
) S
ON TA.AUTH_SEQ = S.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 2 OR TA.AUTH_DEPTH = 3
# GROUP BY TA.AUTH_SEQ
ORDER BY UPPER_AUTH_SEQ ASC;

# 시즌일경우 FA 시즌 업로드한 계정 수를 추가한다 해당 값은 전 시즌 일 이후부터 현재 즌일까지 일자를 넣은뒤에
# ACCOUNT_COUNT 에 넣어진 값을 적는다.


# 시즌	데이터 기간 정의		월간 리포트 구분
# SP
#   전년도 12월 16일~1월 15일	1월 리포트
# 	1월 16일~2월 15일	2월 리포트
# 	2월 16일~3월 15일	SP 시즌 리포트
# SU
#   3월 16일~4월 15일	4월 리포트
# 	4월 16일~5월 15일	5월 리포트
# 	5월 16일~6월 15일	SU 시즌 리포트
# FA
#   6월 16일~7월 15일	7월 리포트
# 	7월 16일~8월 15일	8월 리포트
# 	8월 16일~9월 15일	FA 시즌 리포트
# HO
#   9월 16일~10월 15일	10월 리포트
# 	10월 16일~11월 15일	11월 리포트
# 	11월 16일~12월 15일	HO 시즌 리포트

# su 시즌일경우 3월 16일 부터 6월 15일 까지 sql문을 돌린다음 ACCOUNT_COUNT 값을 적으면된다.