CREATE OR REPLACE procedure proc_login(p_id in varchar2
,p_pw in varchar2, status out number, msg out varchar2)
IS
BEGIN
    SELECT NVL((SELECT 1 FROM dual --���̵� �����ϸ�
                 WHERE EXISTS(SELECT mem_name FROM tomato_member
                               WHERE mem_id =p_id)
              ),-1) into status
      FROM dual;
    IF status = 1 THEN -- 1�̸� ����� �˻��� ����?
       SELECT NVL ((SELECT mem_name FROM tomato_member
                     WHERE mem_id = p_id
                       AND mem_pw = p_pw), '��й�ȣ�� Ʋ���ϴ�.') into msg
        FROM dual;
    ELSE
        msg := '���̵� �������� �ʽ��ϴ�.';
    END IF;
END;
/