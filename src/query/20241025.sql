SELECT deptno,dname,loc FROM dept;

SELECT empno as "사원번호", ename as "사원명", deptno as "부서번호"
    FROM emp;
    
SELECT empno,ename, deptno
    FROM emp;
    
SELECT 1,2,3 FROM dual;

SELECT 1 FROM dual
UNION ALL
SELECT 2 FROM dual
UNION ALL
SELECT 3 FROM dual;

SELECT empno FROM emp; 

SELECT empno FROM emp; 

-- 인덱스가 있는 컬럼은 order by 쓰지 않아도 오름차순이 디폴트이다.
-- 인덱스 만들때 디폴트로 asc넣어준다.

1. parsing - 파싱 - 문법적으로 문제가 있는지 체크
2. DBMS가 실행 계획을 세운다.
3. 옵티마이저에게 넘긴다.
4. open..cursor..fetch..close

부서집합 - 4건 - 인덱스 - 순번 - 사원집합 - 인덱스(empno) - deptno(외래키)
부서집합의 부서 번호와 사원집합의 부서 번호가 같은 것
양쪽 테이블에 모두 존재하는 경우만 조회된다.

SELECT
    d.deptno, e.ename, d.dname
FROM emp e,dept d
WHERE e.deptno=d.deptno;

SELECT distinct(deptno) FROM emp;

SELECT deptno FROM dept
MINUS
SELECT deptno FROM emp;

DELETE FROM dept WHERE deptno=40;

rollback;

--hint문

SELECT /*+index_desc(emp pk_empno)*/empno FROM emp;

SELECT ename FROM emp
ORDER BY ename asc;

SELECT rowid rid FROM emp;

SELECT ename
FROM emp
WHERE rowid='AAARE8AAEAAAACTAAB';

SELECT ename FROM emp;

SELECT
        mem_nick, mem_name
    FROM tomato_member
    WHERE mem_id=:user_id
    AND mem_pw=:user_pw;
    
    SELECT mem_nick, mem_name 
    FROM tomato_member 
    WHERE mem_id='tomato' AND mem_pw =123