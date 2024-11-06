-- 아이디와 비번이 모두 일치하면 반환값은
-- 아이디는 존재하나 비번이 맞지 않으면 반환값은 0
-- 아이디가 존재하지 않으면 -1을 반환하는 쿼리문작성

--상태값이 3가지이다.

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
   
   --컬럼명이 오는 자리에 rownum을 사용하면 조회 결과 목록에 순번을 매긴다.
   --전체 범위 처리를 부분범위 처리로 개선할 때도 사용할 수 있다.
   --(rownum이 stop key의 역활을 수행할 수 있다.)
   
   SELECT rownum rno FROM emp;
   
   사원번호를 채번하는 쿼리문을 작성하시오.
   -- 사원번호 최대값에 1을 더한 값을 새로운 사원의 사원번호를 채번한다.
   
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