SELECT b_no, b_name, b_author, b_publish, b_info, b_img
    FROM book152;
    
-- 시퀀스 값이 다르기만 하다면 나머지 컬럼은 같은 값이어도 추가됩니다.    
    
commit;

SELECT seq_book152_no.nextval FROM dual; 

--다음으로 오는 번호 

SELECT seq_book152_no.currval FROM dual; 

--현재 가지고 있는 번호

-- 1row이면 BookVO로 받을 수 있다. 학습목표
-- nrow이면 List(ArrayList, Vector)

-- 조건 검색하기

SELECT b_no, b_name, b_author, b_publish, b_info, b_img
    FROM book152
    WHERE b_no =:x;
    
-- BookVO pdvo -> if(pdvo.getB_no()>0), if(bvo.getB_no()>0)가 아니라
-- bvo는 select한 결과를 담는 클래스이고 pbvo는 화면에서 사용자가 입력한 값을 담는 클래스이다.
-- 메소드의 파라미터 자리는 사용자가 입력하는 자리이다. - 상수값으로 테스트 할 수 있다면
-- 통합테스트에 참여하지 못하더라도 괜찮아

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