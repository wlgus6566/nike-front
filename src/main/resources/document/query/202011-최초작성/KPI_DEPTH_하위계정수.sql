SELECT COUNT(*)
FROM TB_AUTH
WHERE AUTH_DEPTH = 3


SELECT COUNT(TUA.USER_SEQ)
FROM TB_USER_AUTH TUA
LEFT JOIN TB_AUTH TA
    on TA.AUTH_SEQ = TUA.AUTH_SEQ
WHERE TA.AUTH_DEPTH = 1;


SELECT COUNT(*) FROM TB_USER;

SELECT COUNT(*) FROM TB_AUTH;