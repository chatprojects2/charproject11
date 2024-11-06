SELECT * FROM zipcode_t;

SELECT zipcode, address FROM zipcode_t;

SELECT zdo FROM zipcode_t;

SELECT distinct(zdo) FROM zipcode_t;

SELECT distinct(zdo) FROM zipcode_t
ORDER BY zdo desc;

CREATE TABLE notice1030(
    n_no number(5) constraints notice1030_no_pk primary key,
    n_title varchar2(100),
    n_writer varchar2(30),
    n_content varchar2(4000),
    n_data varchar2(30)
);