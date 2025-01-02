-- 데이터를 그룹화하고, 함수로 계산하는 방법
-- WHERE은 조건이고 그룹화는 데이터를 그룹으로 묶는 것
-- 그룹 후 함수로 원하는 계산 수행.

-- SQL내에서 사용하는 함수. 평균, 개수, 합계 등을 구하는 "집계 함수",
-- 문자열을 원하는 만큼 잘라내거나 대/소문자를 변경하는 등의 기능을 수행하는 "일반 함수"

-- 그룹 별로 수치를 도출하려고 할때 GROUP BY 키워드를 사용한다.
-- 국가별 회원수, 일별 매출 계산.
-- group by로 계산한 결과를 필터링할때에는 HAVING 절을 사용한다.

SELECT count(id), country FROM users WHERE created_at <= "2011-12-31" GROUP BY country HAVING count(id)>=5;

SELECT count(country) FROM users; -- 이렇게 하면 그냥 총 데이터의 수가 나온다.
SELECT count(DISTINCT country) FROM users; -- 중복 제거하는 키워드 DISTINCT.
-- count 안에 컬럼 숫자도 가능하다.
SELECT count(country), country FROM users GROUP BY country;
-- MIN, MAX, SUM, AVG도 있다.
-- ROUND(value, position) : 소수점 몇째자리 까지인지가 position.
SELECT max(price), min(price), sum(price), round(avg(price),2) AS avgPrice FROM products;

/*
SUBSTR(컬럼명, 시작위치, 가져올 문자 개수) : 시작위치 1부터 시작함에 유의
LENGTH(컬럼명): 문자열의 길이 반환
UPPER(컬럼명), LOWER(컬럼명): 대소문자 변경

일반 함수는 SELECT뿐만 아니라 WHERE에서도 사용 가능.
여러행의 데이터를 종합해 하나의 데이터로 반환하는 집계함수와 달리, 한 행의 데이터에 하나의 결과값을 반환하기 때문.
*/

SELECT count(id) FROM users WHERE country="Korea";
SELECT count(country), country FROM users WHERE country IS NOT NULL GROUP BY country;


