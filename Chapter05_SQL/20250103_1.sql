-- users에서 country별 회원 수 구하기
SELECT country, count(DISTINCT id) FROM users GROUP BY country;

-- users에서 country가 Korea한 회원 중, 마케팅 수신에 동의한 회원 수를 구하기
SELECT country, count(DISTINCT id) AS uniqueUserCnt FROM users WHERE country = "Korea" AND is_marketing_agree=1;

-- users에서 country 별로 마케팅 수신에 동의한 회원 수와 동의하지 않은 회원 수를 구하기
-- group by의 기준이 2개일 수도 있으니까. 두 개 이상 기준 컬럼을 추가하면 데이터가 여러 그룹으로 나뉜다.
-- Argentina-0, Argentina-1 과 같이. 중요한 기준을 앞에 배치에서 시각화와 그룹화에 대한 우선순위를 결정할 수 있따.
SELECT country, is_marketing_agree, count(DISTINCT id) AS uniqueUserCnt FROM users
GROUP BY country, is_marketing_agree ORDER BY country, uniqueUserCnt DESC;

-- select - from - where - group by - having - order by
/*
GROUP BY 정리
1) 주어진 칼럼을 기준으로 데이터를 그룹화하여 "집계함수"와 함께 사용.
2) GROUP BY 뒤에는 그룹화할 컬럼명을 입력. 그룹화한 컬럼에 집계함수를 적용하여 그룹별 계산을 수행
3) GROUP BY에서 두 개 이상의 기준 컬럼을 조건으로 추가하여 여러 그룹으로 분할할 수 있다.
*/
SELECT country, city, count(DISTINCT id) AS uniqueUserCnt FROM users GROUP BY country, city ORDER BY country, uniqueUserCnt DESC;

-- SUBSTR 함수 사용해보기
-- users에서 년-월별 가입 회원 수를 최신순으로 구하기
SELECT substr(created_at,1,7) AS yearMonth, count(DISTINCT id) AS unqiueUserCnt FROM users
GROUP BY yearMonth ORDER BY yearMonth DESC;
-- 원래는 SQL문 실행 순서가 from -> where -> group by -> having -> select -> order by이기 때문에,
-- SELECT 구문에서 만든 ALIAS는 group by 절에 사용할 수 없다. 오라클 DB와 SQL 서버에서는 안된다.
-- 그러나 MySQL이나 PostgreSQL같은 몇몇 DBMS의 경우에는 이를 허용하도록 컴파일하는 것으로 보인다.

SELECT sum(quantity), order_id FROM orderdetails GROUP BY order_id ORDER BY sum(quantity) DESC;
SELECT staff_id, user_id, count(*) FROM orders GROUP BY staff_id, user_id ORDER BY staff_id, count(*) DESC;
SELECT substr(order_date,1,7) AS order_month, count(DISTINCT user_id) FROM orders GROUP BY order_month;

-- HAVING
-- GROUP BY에 대한 조건문.
-- users에서 country가 Korea, USA, France인 회원 숫자를 도출할 것
SELECT count(DISTINCT id), country FROM users WHERE country IN ("Korea","USA","France") GROUP BY country;

-- WHERE을 통해서 원하는 국가만 먼저 필터링한 예시. 회원수가 8 이상인 국가만 필터링하려면? 집계함수를 where절에 넣으면 오류가 난다.
SELECT country, count(DISTINCT id) FROM users GROUP BY country HAVING count(DISTINCT id)>7 ORDER BY 2 desc;

/*
1. WHERE은 GROUP BY보다 먼저 실행되기 때문에 그룹화를 진행하기 이전에 행을 필터링한다.
	하지만 집계함수로 계산된 값의 경우에는 그룹화 이후에 이루어지기 때문에 순서상으로 GROUP BY보다 뒤에 있어야해서, WHERE 절로 처리할 수 없다.
2. 그렇기 때문에 GROUP BY를 사용한 집계 값을 필터링할 때는 HAVING을 사용한다.
 */

-- orders에서 staff_id별 주문 건수와 주문 회원 수를 계산하고, 주문건수가 10건 이상이면서 주문회원수가 40명 이하인 데이터만 출력. 주문 건수 기준으로 내림차수 정렬할것.
SELECT staff_id, count(DISTINCT id), count(DISTINCT user_id) FROM orders
GROUP BY staff_id HAVING count(DISTINCT id)>=10 AND count(DISTINCT user_id)<=40 ORDER BY count(DISTINCT id) desc;

/*
HAVING 정리
GROUP BY 뒤쪽에 위치하며, GROUP BY와 집계함수로 그룹별로 집계한 값을 필터링할 때 사용.
WHERE과 동일한 역할을 수행하지만, 적용 영역의 차이 때문에 주의할 필요가 있다.
HAVING은 GROUP BY 이후 결과에 대한 조건 필터링을 수행.
 */

-- SQL문 실행 순서 - from -> where -> group by -> having -> select -> order by
/*
 1) FROM 을 읽어 데이터가 저장된 위치에 접근, 테이터의 존재 유무를 따진 뒤 테이블을 확인
 2) WHERE을 실행하여 가져올 데이터의 범위 확인
 3) DB에서 가져올 범위가 결정된 데이터에 한하여 집계 연산을 적용할 수 있게 그룹으로 데이터를 나눈다.
 4) HAVING은 바로 그 다음 실행되면서 이미 GROUP BY를 통해 집계 연산 적용이 가능한 상태이기 때문에 수행.
 5) 이후 SELECT를 통해 특정 컬럼, 혹은 집게함수 적용 컬럼을 조회하여 값을 보여준다.
 6) ORDER BY를 통해 특정 컬럼 및 연산 결과를 통한 오름차순 및 내림차순으로 정렬.
 */

SELECT user_id, count(id) FROM orders GROUP BY user_id HAVING count(id)>=7 ORDER BY count(id) DESC;
SELECT country, count(city), count(id) FROM users GROUP BY country
HAVING count(city)>=5 AND count(id)>=3 ORDER BY city DESC, id DESC;
SELECT country, count(id) FROM users WHERE country IN ("Brazil", "USA", "Korea", "Argentina", "Mexico")
GROUP BY country HAVING count(id)>=5 ORDER BY count(id) DESC;

/* 실무상에서의 GROUP BY, HAVING
1. 2025-01에 음식 분류별(한식, 중식, 분식, ...) 주문 건수 집계
SELECT 음식분류, COUNT(DISTINCT 주문ID) AS 주문건수 FROM 주문정보 WHERE 주문시간(월)="2025-01"
GROUP BY 음식