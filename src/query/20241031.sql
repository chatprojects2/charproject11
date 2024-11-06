SELECT n_no, n_title, n_content FROM notice1030;

--���̺��� Ŀ���� ���� �ٴ´�. Ŀ���� �����ϴ� �޼ҵ尡 �ʿ��ߴ�.
--�żҵ带 �������� Ŭ������ �Ǿ���ߴ�. - �׷��� ����� ���ó�� ���� �޶���Ѵ�.
--�׷��� �������̽�(����̽����� �޶� ������ �� ������ �߻�޼ҵ�� ������ ��) - ������ ����
-- ResultSet�� Ŀ���� �����̴µ� �ʿ��� next().previous(). isFirst(): boolean
--�׷��� if���̳� while���̳� for�� ��ȣ�ȿ� �� �� �ִ�.
-- PreparedStatement - ��������(<->��������)
-- SELECT -> pstmt.executeQuery("SELECT��") : ResultSet(�������̽�)
-- INSERT, UPDATE, DELETE -> commit, rollback
-- pstmt.executeUpdate("INSERT��|UPDATE��|DELETE��"):int
--javadoc����

--Restful API - CRUD(�Է�,����,����,��ȸ)- Model����(������ ó�� + ����Ͻ� ����)

INSERT INTO dept(deptno, dname,loc) VALUES(50,'�ѹ���','��õ'); 

commit;

SELECT deptno FROM dept
INTERSECT
SELECT deptno FROM emp;

SELECT empno FROM emp; --pk -> unique index(���Ϲ����� - ��������, not null)
-- �׷��� ���̺��� access���� �ʰ� index�� �а� ����� ����Ѵ�.

-- index - ���� ������ �ȴ�. - �ε����� �����ϴ� ������ ������ �ִ�.- �ε��� ���� ����

1) �ǽ�
2) RDBMS�� �����ȹ�� �����.
3) ��Ƽ�������� �����ȹ�� �޾Ƽ� ó���Ѵ�.(INSERT, UPDATE, DELETE)
4) OPEN.. CURSOR(��ġ�� �����Ͱ� �����ϸ� true, false ��ȯ - �ñ׳�)- Fetch ... CLOSE

SELECT ename FROM emp; --������ �ȵǾ��ִ�. �ֳĸ� �ε����� ����. ��? �� PK�� �ƴϾ�

SELECT ename FROM emp
ORDER BY ename asc;

-- hint���� ���ؼ� ��Ƽ���������� �������� �����ȹ�� ������ �� �ִ�.
-- ���� ��Ÿ ������ ���õȴ�. - ������ �ȴ�. - ���� ��ȹ�� �ݿ����� �ʴ´�.

SELECT  /*+index_desc(emp_pk_emp) */ empno
    FROM emp;



-- FK �ܷ�Ű, �ε����� �������� �ʽ��ϴ�. �ߺ��ȴ�.

-- ��������� deptno�� �μ����հ� 1:n����� �߰��� �÷��ε� �̰��� �ܷ�Ű��� �Ѵ�.

SELECT count(deptno) FROM emp WHERE deptno=30;

SELECT count(deptno) FROM emp WHERE deptno10;

SELECT distinct(deptno) FROM emp;

SELECT emp.ename, emp.sal, emp.hiredate, dept.dname
    FROM dept, emp
    WHERE dept.deptno = emp.deptno;

SELECT emp.ename, emp.sal, emp.hiredate, dept.dname, deptno
    FROM emp;

SELECT deptno FROM dept
MINUS
SELECT deptno FROM emp;

DELETE FROM dept WHERE deptno=:x;

SELECT * FROM dept WHERE deptno=50;

--executeUpdate():int

UPDATE dept 
    SET dname='���ߺ�'
        ,loc='����'
WHERE deptno = 50; -- executeUpdate(update�� - ó��) : int ->lrow updataed

--if(result ==1) ������ �����Ͽ����ϴ�. else ������ �����Ͽ����ϴ�. - ����

DELETE FROM dept WHERE deptno=:x;

commit;

--���̵� �ִ��� ���ϱ�

SELECT 1 FROM dual;

SELECT NVL(comm, 0), comm FROM emp;

SELECT
        NVL((SELECT 1 FROM dual
                WHERE EXISTS (SELECT mem_name FROM tomato_member
                                WHERE mem_id=:user_id)
            ),-1) isID                    
    FROM dual;

--���̵� ������ ��� ���ϱ�

