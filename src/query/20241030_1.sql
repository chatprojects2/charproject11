-- dual : 오라클에서 제공하는 로우 1개, 컬럼 1개를 갖는 가상 테이블입니다.

SELECT sysdate FROM dual;

SELECT to_char(sysdate,'YYYY-MM-DD')
        ,to_char(sysdate,'YYYY-MM-DD HH:MI:SS AM')
    FROM dual;
    
SELECT 1,2,3 FROM dual;

SELECT 1 FROM dual
UNION ALL
SELECT 2 FROM dual
UNION ALL
SELECT 3 FROM dual;

SELECT seq_notice1030.nextval FROM dual;

INSERT INTO notice1030(n_no,n_title, n_writer, n_content, n_data)
VALUES(seq_notice1030.nextval,'공지제목1', '치타', '공지제목1내용',to_char(sysdate, 'YYYY-MM-DD'));

INSERT INTO notice1030(n_no, n_title, n_writer, n_content, n_data)
VALUES(seq_notice1030.nextval,'공지제목2', '호랑이', '공지제목2내용',to_char(sysdate, 'YYYY-MM-DD'));

INSERT INTO notice1030(n_no, n_title, n_writer, n_content, n_data)
VALUES(seq_notice1030.nextval,'공지제목3', '표범', '공지제목3내용',to_char(sysdate, 'YYYY-MM-DD'));

SELECT*FROM notice1030;

commit;