-- 1. SUB QUERY
-- 1-1. 단일 행 서브쿼리
-- SELECT, FROM, WHERE 절에서 사용가능한 서브쿼리
SELECT PNO
	, PNAME
	FROM PROFESSOR
	WHERE HIREDATE < (
						SELECT HIREDATE 
							FROM PROFESSOR
							WHERE PNAME = '송강'
		
					  );

-- 손하늘 사원보다 급여(연봉)가 높은 사원의 사원번호, 사원이름, 급여 조회
SELECT ENO 
	, ENAME 
	FROM EMP
	WHERE SAL > (
					SELECT SAL 
						FROM EMP
						WHERE ENAME = '손하늘'
				);

-- 위 쿼리를 JOIN절로 변경
SELECT E.ENO
	, E.ENAME 
	, E.SAL 
	, A.SAL
	FROM EMP E
	JOIN (
		SELECT SAL 
			FROM EMP
			WHERE ENAME = '손하늘'
	
	) A
	ON E.SAL > A.SAL;
	
-- 공용의 일반화학 기말고사 성적보다 높은 학생의 학생번호, 학생이름, 과목번호, 과목이름, 기말고사 성적 조회
--SELECT ST.SNO 
--	, ST.SNAME 
--	, C.CNO
--	, C.CNAME
--	FROM STUDENT ST
--	-- 전체학생 기말고사 성적 연결
--	JOIN SCORE S
--	  ON ST.SNO = S.SNO
--	-- 전체과목과 성적 연결
--	JOIN COURSE C
--	  ON S.CNO = C.CNO
--	-- 공용의 테이블 만들기
--	JOIN (
--		SELECT
--			FROM STUDENT
--			WHERE SNAME = '공용'
--	) ST2
--	-- 공용의 성적보다 높은 학생 찾기
--	  ON S.RESULT > ST2.;

SELECT SC.RESULT
	FROM SCORE SC
	JOIN STUDENT ST
	  ON SC.SNO = ST.SNO 
	JOIN COURSE C
	  ON SC.CNO = C.CNO 
	WHERE ST.SNAME = '공융'
	AND C.CNAME = '일반화학';

	
SELECT SC.RESULT
		, ST.SNO 
		, ST.SNAME 
		, C.CNAME 
	FROM SCORE SC
	JOIN STUDENT ST
	  ON SC.SNO = ST.SNO 
	JOIN COURSE C
	  ON SC.CNO = C.CNO 
	JOIN (
		SELECT SC.RESULT
		FROM SCORE SC
		JOIN STUDENT ST
	  	  ON SC.SNO = ST.SNO 
		JOIN COURSE C
	  	  ON SC.CNO = C.CNO 
		WHERE ST.SNAME = '공융'
		AND C.CNAME = '일반화학'
	) A 
	ON SC.RESULT > A.RESULT
	AND C.CNAME = '일반화학';
	
-- 1-2. 다중행 서브쿼리
-- 서브쿼리의 결과가 여러행인 서브쿼리
-- FROM, JOIN, WHERE 절에서 사용가능
-- 급여가 3000이상인 사원의 사원번호, 사원이름, 급여 조회
SELECT ENO, ENAME, SAL
	FROM EMP
	WHERE SAL >= 3000;

SELECT E.ENO
	 , E.ENAME
	 , E.SAL
	FROM EMP E
	JOIN (
		SELECT ENO
			FROM EMP
			WHERE SAL >= 3000
	) A
	  ON E.ENO = A.ENO;
	
-- WHERE 절에서 사용
SELECT E.ENO
	 , E.ENAME
	 , E.SAL
	FROM EMP E
	WHERE E.ENO IN (
		SELECT ENO
			FROM EMP
			WHERE SAL >= 3000
	);	 

-- 1-3. 다중열 서브쿼리
-- 서브쿼리의 결과가 다중행이면서 다중열인 서브쿼리
-- FROM, JOIN 절에서만 사용가능
-- 과목번호, 과목이름, 교수번호, 교수이름을 조회하는 서브쿼리를 작성하여
-- 기말고사 성적 테이블과 조인하여 과목번호, 과목이름, 교수번호, 교수이름, 기말고사 성적을 조회
SELECT C.CNO 
	, C.CNAME 
	, P.PNO
	, P.PNAME
	, SC.RESULT
	FROM COURSE C
	JOIN SCORE SC
	  ON C.CNO = SC.CNO 
	JOIN PROFESSOR P
	  ON C.PNO = P.PNO;

SELECT A.CNO
	, A.CNAME
	, A.PNO
	, A.PNAME
	, SC.RESULT
	FROM (
		SELECT C.CNO
			, C.CNAME
			, P.PNO
			, P.PNAME
			FROM COURSE C
			JOIN PROFESSOR P
			  ON C.PNO = P.PNO
	) A
	JOIN SCORE SC
	  ON A.CNO = SC.CNO;
	  
-- 서브쿼리는 그룹함수와 주로 사용된다.
SELECT ST.SNO 
	, ST.SNAME 
	, AVG(SC.RESULT) 
	FROM STUDENT ST
	JOIN SCORE SC
	  ON SC.SNO = ST.SNO 
	GROUP BY ST.SNO , ST.SNAME;
	
-- 학생번호, 학생이름, 과목번호, 과목이름, 기말고사 성적, 기말고사 성적 등급, 담당 교수번호, 담당 교수이름 조회하는 데
-- STUDENT, SCORE, SCGRADE 테이블의 내용을 서브쿼리1
-- COURSE, PROFESSOR 테이블의 내용을 서브쿼리2
SELECT A.SNO
	, A.SNAME
	, B.CNO
	, B.CNAME
	, A.RESULT
	, A.GRADE
	, B.PNO
	, B.PNAME
	FROM (
		SELECT ST.SNO
			, ST.SNAME
			, SC.RESULT
			-- B와 공통된 컬럼을 뽑아주기 위해 CNO 추가 
			, SC.CNO
			, SG.GRADE
			FROM STUDENT ST
			JOIN SCORE SC
			  ON ST.SNO = SC.SNO
			JOIN SCGRADE SG
			  ON SC.RESULT BETWEEN SG.LOSCORE AND SG.HISCORE
	) A
	JOIN (
		SELECT C.CNO
			, C.CNAME
			, P.PNO
			, P.PNAME
			FROM COURSE C
			JOIN PROFESSOR P
			  ON C.PNO = P.PNO
	) B
	ON A.CNO = B.CNO;