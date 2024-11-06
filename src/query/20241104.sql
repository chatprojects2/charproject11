INSERT INTO book152 VALUES(1,'혼자공부하는 파이썬','윤인성','한빛미디어','혼자 해도 충분하다! 1:1 과외하듯 배우는 파이썬 프로그래밍 자습서(파이썬 최신 버전 반영) 이 책은 독학으로 프로그래밍 언어를 처음 배우려는 입문자가, 혹은 파이썬을 배우려는 입문자가 ‘꼭 필요한 내용을 제대로’ 학습할 수 있도록 구성했다. ‘무엇을’, ‘어떻게’ 학습해야 할지조차 모르는 입문자의 막연한 마음을 살펴, 과외 선생님이 알려주듯 친절하게, 그러나 핵심적인 내용만 콕콕 집어준다. 책의 첫 페이지를 펼쳐서 마지막 페이지를 덮을 때까지, 혼자서도 충분히 파이썬을 배울 수 있다는 자신감과 확신이 계속될 것이다!','1.jpg');

INSERT INTO book152 VALUES(2,'Do it 점프 투 파이썬','박응용','이지스퍼블리싱','문과생도 중고등학생도 직장인도 프로그래밍에 눈뜨게 만든 바로 그 책이 전면 개정판으로 새로 태어났다! 2016년 《Do it! 점프 투 파이썬》으로 출간되었던 이 책은 약 4년 동안의 피드백을 반영하여 초보자가 더 빠르게 입문하고, 더 깊이 있게 공부할 수 있도록 개정되었다. 특히 ‘나 혼자 코딩’과 ‘코딩 면허 시험 20제’ 등 독자의 학습 흐름에 맞게 문제를 보강한 점이 눈에 띈다. 실습량도 두 배로 늘었다.4년 동안 압도적 1위! 위키독스 누적 방문 200만! 수많은 대학 및 학원의 교재 채택 등! 검증은 이미 끝났다. 코딩을 처음 배우는 중고등학생부터 나만의 경쟁력이 필요한 문과생, 데이터 분석과 인공지능/머신러닝으로 커리어를 뻗어 나가고 싶은 직장인까지! 프로그래밍의 세계에 풍덩 빠져 보자.','2.jpg');

INSERT INTO book152 VALUES(3,'파이썬 머신러닝 완벽가이드','권철민','위키북스','《파이썬 머신러닝 완벽 가이드》는 이론 위주의 머신러닝 책에서 탈피해 다양한 실전 예제를 직접 구현해 보면서 머신러닝을 체득할 수 있도록 만들었습니다. 캐글과 UCI 머신러닝 리포지토리에서 난이도가 있는 실습 데이터를 기반으로 실전 예제를 구성했고, XGBoost, LightGBM, 스태킹 기법 등 캐글의 많은 데이터 사이언스에서 애용하는 최신 알고리즘과 기법에 대해 매우 상세하게 설명했습니다. 이번 개정판에서는 사이킷런 및 기타 라이브러리의 업데이트에 따른 전반적인 내용 및 소스코드 업데이트와 함께 질의 사항이 많은 부분들에 대한 상세한 설명을 추가했습니다.','3.jpg');

INSERT INTO book152 VALUES(4,'책제목','강감찬','정철사','책내용', '4.jpg');

UPDATE book152
    SET b_name='책제목2'
        ,b_author='이순신'
    WHERE b_no=4;

DELETE FROM book152
    WHERE b_no=4;

commit;

-- 커밋을 하면 롤백이 안된다.
-- 커밋을 해야 물리적인 테이블에 반영됨

SELECT*FROM book152 WHERE b_no=4;

executeQuery() : ResultSet - select

executeUpdate(): int - insert or delect or update

SELECT*FROM book152;

-- 한 건만 조회할 때는 pk컬럼을 이용합니다.
-- unique, not null

SELECT*FROM book152 WHERE b_no=2;

SELECT*FROM book152 WHERE b_no=:b;

SELECT*FROM book152
    WHERE b_author LIKE '권'||'%';
    
SELECT*FROM book152
    WHERE b_author LIKE '%'||'권';
    
SELECT*FROM book152
    WHERE b_author LIKE '%'||'권'||'%';
    
SELECT*FROM book152
    WHERE b_info LIKE '%'||'머신러닝'||'%';
    
SELECT*FROM book152
    WHERE b_info LIKE '머신러닝'||'%';
    
SELECT*FROM book152
    WHERE b_info LIKE '%'||'머신러닝';
    
SELECT*FROM book152
    WHERE b_name LIKE '%'||'머신러닝'||'%';
    
SELECT*FROM book152
    WHERE b_name LIKE '머신러닝'||'%';
    
SELECT*FROM book152
    WHERE b_name LIKE '%'||'머신러닝';
    
SELECT zipcode,address
    FROM zipcode_t
    WHERE dong LIKE '%'||'가산동'||'%';
    
SELECT zipcode,address
    FROM zipcode_t
    WHERE dong LIKE '%'||'가산';
    
-- like문 구간 검색, 부분검색, range scan
    
SELECT zipcode,address
    FROM zipcode_t
    WHERE dong LIKE '가산'||'%';