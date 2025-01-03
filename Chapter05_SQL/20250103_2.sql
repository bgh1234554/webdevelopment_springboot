/* 1. JOIN
 * 2. UNION
 * 3. SUBQUERY란?
 */

/*
JOIN - 여러 테이블에서 데이터를 가져와 기능을 제공.
RDBMS에서는 데이터의 중복을 피하고 쉽게 관리하기 위해 데이터를 여러 곳에 나누어 보관함.
데이터 분산을 데이터 정규화라고 하며, 데이터베이스에서 중복을 최소화하고 데이터의 일관성을 유지하기 위함.

1) JOIN - 둘 이상의 테이블을 함께 다루는 연습.
ex) 국가별 주문 건수 -> users, orders가 필요함.

수학에서의 집합 개념과 사실상 동일. */

SELECT * FROM users u INNER JOIN orders o ON u.id=o.user_id ORDER BY u.id;
-- NULL 값인거는 안뽑음.
SELECT * FROM users u LEFT OUTER JOIN orders o ON u.id=o.user_id ORDER BY u.id;
