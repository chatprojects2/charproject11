-- ���̵�� ����� ��� ��ġ�ϸ� ��ȯ����
-- ���̵�� �����ϳ� ����� ���� ������ ��ȯ���� 0
-- ���̵� �������� ������ -1�� ��ȯ�ϴ� �������ۼ�

--���°��� 3�����̴�.

SELECT
        CLASS WHEN mem_id=:id THEN
        CLASS WHEN mem_pw=:pw THEN 1
        ELSE 0
        END
       ELSE -1
       END as result
    FROM tomato_member
   WHERE rownum=1;
   
   SELECT rownum rno, deptno, dname, loc FROM dept;
   
   SELECT 1,2,3,4,5 FROM dual;
   
   SELECT 1 FROM dual
   UNION ALL
   SELECT 2 FROM dual
   UNION ALL
   SELECT 3 FROM dual
   
   --�÷����� ���� �ڸ��� rownum�� ����ϸ� ��ȸ ��� ��Ͽ� ������ �ű��.
   --��ü ���� ó���� �κй��� ó���� ������ ���� ����� �� �ִ�.
   --(rownum�� stop key�� ��Ȱ�� ������ �� �ִ�.)
   
   SELECT rownum rno FROM emp;
   
   �����ȣ�� ä���ϴ� �������� �ۼ��Ͻÿ�.
   -- �����ȣ �ִ밪�� 1�� ���� ���� ���ο� ����� �����ȣ�� ä���Ѵ�.
   
   SELECT max(empno) FROM emp;
   
   SELECT ename FROM emp;
   
   SELECT 
        /*+index_desc(emp pk_emp)*/ empno
    FROM emp;
   WHERE rownum =1;
   
   SELECT
        1+1 as new_empno
    FROM dual;
    
   SELECT ((SELECT/*+index_desc(emp pk_emp)*/ empno
            FROM emp
           WHERE rownum =1)+1) as new_empno
     FROM dual;
   
   SELECT ename FROM emp;