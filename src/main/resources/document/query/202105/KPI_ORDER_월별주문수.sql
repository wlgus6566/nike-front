-- 월별 주문수
    -- 주문 수 기준
SELECT COUNT(*) AS COUNT
FROM TB_ORDER TOS
WHERE TOS.REGISTRATION_DT BETWEEN '2021-09-16 00:00:00' AND '2021-10-15 23:59:59'
;


-- 카테고리 별 주문수
    -- 상품 카테고리 기준
SELECT TP.CATEGORY2_CODE, COUNT(*)
FROM TB_ORDER TOS
LEFT JOIN TB_ORDER_PRODUCT_MAPPING TOPM
ON TOS.ORDER_SEQ = TOPM.ORDER_SEQ
LEFT JOIN TB_PRODUCT TP
ON TOPM.GOODS_SEQ = TP.GOODS_SEQ
WHERE TOS.REGISTRATION_DT BETWEEN '2021-09-16 00:00:00' AND '2021-10-15 23:59:59'
GROUP BY TP.CATEGORY2_CODE;
