INSERT INTO book152 VALUES(1,'ȥ�ڰ����ϴ� ���̽�','���μ�','�Ѻ��̵��','ȥ�� �ص� ����ϴ�! 1:1 �����ϵ� ���� ���̽� ���α׷��� �ڽ���(���̽� �ֽ� ���� �ݿ�) �� å�� �������� ���α׷��� �� ó�� ������ �Թ��ڰ�, Ȥ�� ���̽��� ������ �Թ��ڰ� ���� �ʿ��� ������ ����Ρ� �н��� �� �ֵ��� �����ߴ�. ����������, ����ԡ� �н��ؾ� �������� �𸣴� �Թ����� ������ ������ ����, ���� �������� �˷��ֵ� ģ���ϰ�, �׷��� �ٽ����� ���븸 ���� �����ش�. å�� ù �������� ���ļ� ������ �������� ���� ������, ȥ�ڼ��� ����� ���̽��� ��� �� �ִٴ� �ڽŰ��� Ȯ���� ��ӵ� ���̴�!','1.jpg');

INSERT INTO book152 VALUES(2,'Do it ���� �� ���̽�','������','�������ۺ�����','�������� �߰����л��� �����ε� ���α׷��ֿ� ���߰� ���� �ٷ� �� å�� ���� ���������� ���� �¾��! 2016�� ��Do it! ���� �� ���̽㡷���� �Ⱓ�Ǿ��� �� å�� �� 4�� ������ �ǵ���� �ݿ��Ͽ� �ʺ��ڰ� �� ������ �Թ��ϰ�, �� ���� �ְ� ������ �� �ֵ��� �����Ǿ���. Ư�� ���� ȥ�� �ڵ����� ���ڵ� ���� ���� 20���� �� ������ �н� �帧�� �°� ������ ������ ���� ���� ���. �ǽ����� �� ��� �þ���.4�� ���� �е��� 1��! ��Ű���� ���� �湮 200��! ������ ���� �� �п��� ���� ä�� ��! ������ �̹� ������. �ڵ��� ó�� ���� �߰����л����� ������ ������� �ʿ��� ������, ������ �м��� �ΰ�����/�ӽŷ������� Ŀ��� ���� ������ ���� �����α���! ���α׷����� ���迡 ǳ�� ���� ����.','2.jpg');

INSERT INTO book152 VALUES(3,'���̽� �ӽŷ��� �Ϻ����̵�','��ö��','��Ű�Ͻ�','�����̽� �ӽŷ��� �Ϻ� ���̵塷�� �̷� ������ �ӽŷ��� å���� Ż���� �پ��� ���� ������ ���� ������ ���鼭 �ӽŷ����� ü���� �� �ֵ��� ��������ϴ�. ĳ�۰� UCI �ӽŷ��� �������丮���� ���̵��� �ִ� �ǽ� �����͸� ������� ���� ������ �����߰�, XGBoost, LightGBM, ����ŷ ��� �� ĳ���� ���� ������ ���̾𽺿��� �ֿ��ϴ� �ֽ� �˰������ ����� ���� �ſ� ���ϰ� �����߽��ϴ�. �̹� �����ǿ����� ����Ŷ�� �� ��Ÿ ���̺귯���� ������Ʈ�� ���� �������� ���� �� �ҽ��ڵ� ������Ʈ�� �Բ� ���� ������ ���� �κе鿡 ���� ���� ������ �߰��߽��ϴ�.','3.jpg');

INSERT INTO book152 VALUES(4,'å����','������','��ö��','å����', '4.jpg');

UPDATE book152
    SET b_name='å����2'
        ,b_author='�̼���'
    WHERE b_no=4;

DELETE FROM book152
    WHERE b_no=4;

commit;

-- Ŀ���� �ϸ� �ѹ��� �ȵȴ�.
-- Ŀ���� �ؾ� �������� ���̺��� �ݿ���

SELECT*FROM book152 WHERE b_no=4;

executeQuery() : ResultSet - select

executeUpdate(): int - insert or delect or update

SELECT*FROM book152;

-- �� �Ǹ� ��ȸ�� ���� pk�÷��� �̿��մϴ�.
-- unique, not null

SELECT*FROM book152 WHERE b_no=2;

SELECT*FROM book152 WHERE b_no=:b;

SELECT*FROM book152
    WHERE b_author LIKE '��'||'%';
    
SELECT*FROM book152
    WHERE b_author LIKE '%'||'��';
    
SELECT*FROM book152
    WHERE b_author LIKE '%'||'��'||'%';
    
SELECT*FROM book152
    WHERE b_info LIKE '%'||'�ӽŷ���'||'%';
    
SELECT*FROM book152
    WHERE b_info LIKE '�ӽŷ���'||'%';
    
SELECT*FROM book152
    WHERE b_info LIKE '%'||'�ӽŷ���';
    
SELECT*FROM book152
    WHERE b_name LIKE '%'||'�ӽŷ���'||'%';
    
SELECT*FROM book152
    WHERE b_name LIKE '�ӽŷ���'||'%';
    
SELECT*FROM book152
    WHERE b_name LIKE '%'||'�ӽŷ���';
    
SELECT zipcode,address
    FROM zipcode_t
    WHERE dong LIKE '%'||'���굿'||'%';
    
SELECT zipcode,address
    FROM zipcode_t
    WHERE dong LIKE '%'||'����';
    
-- like�� ���� �˻�, �κа˻�, range scan
    
SELECT zipcode,address
    FROM zipcode_t
    WHERE dong LIKE '����'||'%';