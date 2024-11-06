SELECT n_no, n_title, n_content FROM notice1030;

--테이블에는 커서가 따라 붙는다. 커서를 조작하는 메소드가 필요했다.
--매소드를 가지려면 클래스가 되어야했다. - 그런데 기능은 사용처에 따라 달라야한다.
--그러면 인터페이스(디바이스마다 달라서 결정할 수 없으면 추상메소드로 정의할 것) - 다형성 실현
-- ResultSet은 커서를 움직이는데 필요한 next().previous(). isFirst(): boolean
--그래서 if문이나 while문이나 for문 괄호안에 쓸 수 있다.
-- PreparedStatement - 동적쿼리(<->정적쿼리)
-- SELECT -> pstmt.executeQuery("SELECT문") : ResultSet(인터페이스)
-- INSERT, UPDATE, DELETE -> commit, rollback
-- pstmt.executeUpdate("INSERT문|UPDATE문|DELETE문"):int
--javadoc문법

--Restful API - CRUD(입력,수정,삭제,조회)- Model계층(데이터 처리 + 비즈니스 로직)

INSERT INTO dept(deptno, dname,loc) VALUES(50,'총무부','인천'); 

commit;

SELECT deptno FROM dept
INTERSECT
SELECT deptno FROM emp;

SELECT empno FROM emp; --pk -> unique index(유일무이한 - 제약조건, not null)
-- 그래서 테이블을 access하지 않고 index만 읽고서 결과를 출력한다.

-- index - 따로 관리가 된다. - 인덱스만 관리하는 별도의 공간이 있다.- 인덱스 전략 수립

1) 피싱
2) RDBMS가 실행계획을 세운다.
3) 옵티마이저가 실행계획을 받아서 처리한다.(INSERT, UPDATE, DELETE)
4) OPEN.. CURSOR(위치에 데이터가 존재하면 true, false 반환 - 시그널)- Fetch ... CLOSE

SELECT ename FROM emp; --정렬이 안되어있다. 왜냐면 인덱스가 없다. 왜? 넌 PK가 아니야

SELECT ename FROM emp
ORDER BY ename asc;

-- hint문을 통해서 옵티마이저에게 개발자의 실행계획을 전달할 수 있다.
-- 만일 오타 있으면 무시된다. - 실행은 된다. - 실행 계획이 반영되지 않는다.

SELECT  /*+index_desc(emp_pk_emp) */ empno
    FROM emp;



-- FK 외래키, 인덱스가 제공되지 않습니다. 중복된다.

-- 사원집합의 deptno는 부서집합과 1:n관계로 추가된 컬럼인데 이것을 외래키라고 한다.

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
    SET dname='개발부'
        ,loc='제주'
WHERE deptno = 50; -- executeUpdate(update문 - 처리) : int ->lrow updataed

--if(result ==1) 수정에 성공하였습니다. else 수정에 실패하였습니다. - 응답

DELETE FROM dept WHERE deptno=:x;

commit;

--아이디가 있는지 비교하기

SELECT 1 FROM dual;

SELECT NVL(comm, 0), comm FROM emp;

SELECT
        NVL((SELECT 1 FROM dual
                WHERE EXISTS (SELECT mem_name FROM tomato_member
                                WHERE mem_id=:user_id)
            ),-1) isID                    
    FROM dual;

--아이디가 있으면 비번 비교하기

