/*
UNION
1. 컬럼 목록이 같은 데이터를 위아래로 결합. 위 아래로 수직 결합해주는 기능.
	데이터를 위 아래로 수직 결합해주는 기능. -> 컬럼의 형식과 개수가 같은 두 데이터 결과 집합을 하나로 결합.
	결과 집합에 대해 중복을 제거하고, 정렬을 수행해준다.
	중복 제거를 원하지 않으면 UNION ALL 명령어를 사용하면 된다.
INTERSCT -> UNION과 똑같은 형태지만 교집합 결과를 출력.
*/
SELECT id, phone, city, country FROM users UNION ALL SELECT id, phone, city, country FROM users ORDER BY id;
-- 실무에서는 UNION ALL이 더 권장된다. 대량의 데이터를 대상으로 중복 제거할때 컴퓨터에 무리한 연산 부하를 줄 가능성이 있기 때문이다.
-- UNION ALL을 통해서 최종 결과 형태 확인 후에 UNION을 적용하는 방식으로 짜여있는 곳이 많다.

SELECT id, phone, city, country FROM users WHERE country="Korea"
UNION
SELECT id, phone, city, country FROM users WHERE country="Mexico"
ORDER BY country;

-- SELECT id, phone, city, country FROM users WHERE country IN ("Mexico", "Korea") ORDER BY country;

SELECT id, order_date FROM orders WHERE substr(order_date,1,7)="2015-10"
UNION ALL
SELECT id, order_date FROM orders WHERE substr(order_date,1,7)="2015-12";

-- date 비교할때 order_date>="2015-10-01" AND order_date<"2015-11-01"; 과 같은 방식으로 자료를 추출할 수 있다.
/*
SQL 상에서의 문자 비교 방식
문자열을 왼쪽에서 오른쪽으로 한 문자씩 비교
ASCII / 유니코드 값을 기준으로 비교
왼쪽부터 읽어오다가 다른 문자가 발견되는 순간 그 값에 따라 크고 작음을 판별한다.
 */

SELECT id, phone, country, city, is_marketing_agree FROM users WHERE country="USA" AND is_marketing_agree=1
UNION ALL
SELECT id, phone, country, city, is_marketing_agree FROM users WHERE country="France" AND is_marketing_agree=0
ORDER BY country DESC;

-- UNION을 활용하여 orderdetails와 products를 full outer join 조건으로 결합하여 출력하기
-- MariaDB는 MySQL과는 달리 FULL OUTER JOIN을 지원하지 않는다. 그래서 LEFT JOIN UNION RIGHT JOIN으로 구현한다.
-- 실무에서 자주 쓰지는 않는다.

SELECT * FROM orderdetails o LEFT JOIN products p ON o.product_id = p.id
UNION
SELECT * FROM orderdetails o RIGHT JOIN products p ON o.product_id=p.id;

/*
SubQuery -> 쿼리 속의 쿼리
작성한 쿼리를 소괄호 속에 묶어서 사용하는데, 실제 테이블은 아니지만 테이블처럼 사용이 가능하다.

products에서 name과 price를 모두 불러오고, 평균 정상 가격을 새로운 컬럼으로 각 행마다 출력해보기. */
SELECT name, price, (SELECT avg(price) FROM products) FROM products;

/*
SELECT 절에는 단일값을 반환하는 서브쿼리(스칼라 서브쿼리)가 올 수 있다. 위의 서브쿼리는 전체 상품의 평균.
그냥 평균 값을 숫자 그대로 쿼리 문에 집어넣을 수 있으나, 정확한 값을 얻기 위해서 서브쿼리를 사용하는 것이 효율적이다.

두개 이상의 집계 값을 기존 테이블에 추가하여 출력하고 싶다면, 스칼라 서브쿼리로 따로 나누어서 작성해야 한다.
 */

SELECT * FROM (SELECT city, count(DISTINCT id) AS cntUser FROM users u GROUP BY city) a WHERE cntUser>=3 ORDER BY cntUser DESC;

-- use play_sql -> SQL 파일 수동으로 연결
-- orders와 staff를 활용해 last_name이 Kyle이나 Scott인 직원의 담당 주문을 확인하려면?
SELECT * FROM orders WHERE staff_id IN (SELECT id FROM staff WHERE last_name IN ("Kyle","Scott"));

-- 결과 값으로 직원 정보 테이블에 존재하는 id, user_id와 동일한 값이 orders 테이블의 staff_id, user_id 컬럼에 있을 경우 변환하여 출력한다.
-- products 에서 discount_price가 가장 비싼 제품 정보 출력하기 (단 products의 모든 컬럼이 다 출력되어야 한다.)
SELECT * FROM products WHERE discount_price = (SELECT max(discount_price) FROM products);

-- !! orders에서 주문 월이 2015년 7월인 주문 정보를, orderdetails에서 quantity가 50 이상인 정보를 각각 서브쿼리로 작성하고 INNER JOIN하여 출력해보기.
SELECT * FROM (SELECT * FROM orders WHERE substr(order_date,1,7)="2015-07") a
INNER JOIN (SELECT * FROM orderdetails WHERE quantity>=50) b ON a.id=b.order_id;
-- 결과 1 테이블과 결과 2 테이블을 INNER JOIN하는 방식.

/* 서브 쿼리를 작성하기 위한 방안 중에 하나는 서브 쿼리에 들어가게 될 쿼리문을 작성한 결과값을 확인
 이후 해당 쿼리가 scalar나 아니냐에 따라서 그 위치 어느 정도 통제 가능
 SELECT/FROM/WHERE 등 사용 위치에 따라 불리는 이름이 다르다.
 1. SELECT 절에서 SELECT ... ([서브쿼리]) AS [컬럼명] ...후략...
 단일 집계 값을 신규 컬럼으로 추가하기 위해 서브 쿼리를 사용한다.
 메인 쿼리의 FROM에서 사용된 테이블이 아닌 테이블도 사용이 가능하기 때문에 JOIN 수행을 줄일 수 있다는 장점이 있다.
 2. FROM 절에서 사용되는 서브 쿼리 - "인라인 뷰(Inline View)"라는 용어 사용.
 서브 쿼리의 결과값을 테이블처럼 사용할 수 있다. FROM에서 2개 이상의 서브 쿼리를 활용하여 JOIN 연산이 가능하다.
 이때 JOIN 연산을 위해 별칭 생성이 가능한데, 서브 쿼리가 끝나는 괄호 뒤에 공백을 한 칸 주고 원하는 별칭을 쓰면 된다.
 FROM에서 서브 쿼리를 활용하면 RDBMS 기준으로 적은 연산으로 같은 결과를 도출할 수 있다.
 인덱스 - 테이블의 검색 속도를 높이는 기능으로, 컬럼 값을 정렬하여 검색 시 더욱 빠르게 찾아내도록 하는 자료 구조.
 3. WHERE 절에서
 WHERE에서 필터링을 위한 조건 값을 설정하는데 서브 쿼리 사용 가능. IN말고 ANY와 ALL연산자도 사용할 수 있다.
 IN 연산자의 경우에 다중 컬럼을 비교할 때는 서브 쿼리에서 추출하는 컬럼의 개수와
 WHERE에 작성하는 필터링 대상 컬럼 개수가 일치해야함. -> 필터링 대상이 2개 이상이면 튜플로 묶어야함.

	1. 데이터 그룹화하기 (GROUP BY + 집계함수)
	2. 데이터 결과 집합 결합하기 (JOIN + 서브쿼리)
	3. 테이블 결합 후 그룹화하기 (JOIN + GROUP BY)
	4. 서브 쿼리로 필터링 (WHERE + 서브쿼리)
	5. 같은 행동 반복 대상 추출 (LEFT JOIN)

ex1) users에서 created_at 컬럼 활용하여 연도별 가입 회원 수를 추출
*/
SELECT count(DISTINCT id),substr(created_at,1,4) AS createdYear FROM users
WHERE created_at IS NOT NULL GROUP BY substr(created_at,1,4);

-- ex2) !!users에서 country, city, is_auth 칼럼을 활용해 국가별, 도시별로 본인 인증한 회원수를 추출하라.
-- is_auth=1이면 인증한 회원, is_auth=0이면 인증하지 않은 회원수라 이렇게 하면 좋음.
SELECT country, city, sum(is_auth) FROM users GROUP BY country, city;





