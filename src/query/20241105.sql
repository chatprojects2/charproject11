SELECT b_no, b_name, b_author, b_publish, b_info, b_img
    FROM book152;
    
-- ������ ���� �ٸ��⸸ �ϴٸ� ������ �÷��� ���� ���̾ �߰��˴ϴ�.    
    
commit;

SELECT seq_book152_no.nextval FROM dual; 

--�������� ���� ��ȣ 

SELECT seq_book152_no.currval FROM dual; 

--���� ������ �ִ� ��ȣ

-- 1row�̸� BookVO�� ���� �� �ִ�. �н���ǥ
-- nrow�̸� List(ArrayList, Vector)

-- ���� �˻��ϱ�

SELECT b_no, b_name, b_author, b_publish, b_info, b_img
    FROM book152
    WHERE b_no =:x;
    
-- BookVO pdvo -> if(pdvo.getB_no()>0), if(bvo.getB_no()>0)�� �ƴ϶�
-- bvo�� select�� ����� ��� Ŭ�����̰� pbvo�� ȭ�鿡�� ����ڰ� �Է��� ���� ��� Ŭ�����̴�.
-- �޼ҵ��� �Ķ���� �ڸ��� ����ڰ� �Է��ϴ� �ڸ��̴�. - ��������� �׽�Ʈ �� �� �ִٸ�
-- �����׽�Ʈ�� �������� ���ϴ��� ������

SELECT comm, NVL(comm,0) FROM emp;

SELECT ename, comm FROM emp
WHERE comm is null;

SELECT ename, comm FROM emp
WHERE comm is not null;

SELECT ename, comm FROM emp
ORDER BY comm asc;

SELECT ename, comm FROM emp
ORDER BY comm desc;

SELECT * FROM book152
WHERE b_no IN(5,6);