-- 월별 주문수 
SELECT DT.DATE, IFNULL(RESULT.COUNT , 0) AS COUNT, IFNULL(RESULT.SUM, 0) AS SUM
FROM (
	SELECT CONCAT('2020-', A) AS DATE
	FROM (
		SELECT '01' AS A
		UNION ALL SELECT '02' 
		UNION ALL SELECT '03' 
		UNION ALL SELECT '04' 
		UNION ALL SELECT '05' 
		UNION ALL SELECT '06' 
		UNION ALL SELECT '07' 
		UNION ALL SELECT '08' 
		UNION ALL SELECT '09'
		UNION ALL SELECT '10'
		UNION ALL SELECT '11'
		UNION ALL SELECT '12'
	) AS A 
) DT
LEFT JOIN (
	SELECT DATE_FORMAT(TOS.REGISTRATION_DT, "%Y-%m") AS DATE
			, COUNT(*) AS COUNT
			, SUM(TOS.TOTAL_AMOUNT) AS SUM
	FROM TB_ORDER TOS
	WHERE TOS.REGISTRATION_DT BETWEEN '2020-01-01' AND '2020-11-12'
	GROUP BY DATE_FORMAT(TOS.REGISTRATION_DT, "%Y-%m")
) AS RESULT
ON DT.DATE = RESULT.DATE
;



SELECT TP.CATEGORY2_CODE, COUNT(*), SUM(ORDER_QUANTITY)
FROM TB_ORDER TOS
LEFT JOIN TB_ORDER_PRODUCT_MAPPING TOPM
ON TOS.ORDER_SEQ = TOPM.ORDER_SEQ
LEFT JOIN TB_PRODUCT TP
ON TOPM.GOODS_SEQ = TP.GOODS_SEQ
WHERE TOS.REGISTRATION_DT BETWEEN '2020-01-01 00:00:00' AND '2020-11-30 23:59:59'
GROUP BY TP.CATEGORY2_CODE;
# AND TP.CATEGORY2_CODE = 'SUBSIDIARY';
# AND TP.CATEGORY2_CODE = 'NIKE_BY_YOU';
# AND TP.CATEGORY2_CODE = 'CUSTOM23';
# AND TP.CATEGORY2_CODE = 'MNQ';



SELECT SUM(MM.ORDER_QUANTITY)
FROM TB_ORDER_PRODUCT_MAPPING MM
LEFT JOIN TB_ORDER T on T.ORDER_SEQ = MM.ORDER_SEQ
WHERE T.REGISTRATION_DT BETWEEN '2020-01-01 00:00:00' AND '2020-11-30 23:59:59'

SELECT *
FROM TB_CODE
WHERE UPPER_CODE IN ('SUBSIDIARY', 'NIKE_BY_YOU', 'CUSTOM23', 'MNQ')
