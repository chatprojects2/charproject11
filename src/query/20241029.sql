SELECT * FROM zipcode_t

SELECT zipcode, address
 FROM zipcode_t
WHERE dong = '공덕동';

SELECT zipcode, address
 FROM zipcode_t
WHERE dong like '가산'||'%';

SELECT '전체' zdo FROM dual
UNION ALL
SELECT zdo
  FROM (
        SELECT
               distinct(zdo) zdo
          FROM zipcode_t
        ORDER BY zdo asc
       );

SELECT
       distinct(zdo) zdo
  FROM zipcode_t
ORDER BY zdo desc;