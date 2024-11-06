-- dual : ����Ŭ���� �����ϴ� �ο� 1��, �÷� 1���� ���� ���� ���̺��Դϴ�.

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
VALUES(seq_notice1030.nextval,'��������1', 'ġŸ', '��������1����',to_char(sysdate, 'YYYY-MM-DD'));

INSERT INTO notice1030(n_no, n_title, n_writer, n_content, n_data)
VALUES(seq_notice1030.nextval,'��������2', 'ȣ����', '��������2����',to_char(sysdate, 'YYYY-MM-DD'));

INSERT INTO notice1030(n_no, n_title, n_writer, n_content, n_data)
VALUES(seq_notice1030.nextval,'��������3', 'ǥ��', '��������3����',to_char(sysdate, 'YYYY-MM-DD'));

SELECT*FROM notice1030;

commit;