SELECT substr(created_at,1,7) AS yearMonth, count(DISTINCT id) AS unqiueUserCnt FROM users
GROUP BY yearMonth ORDER BY yearMonth DESC;

SELECT substr(created_at,1,7) AS yearMonth, count(DISTINCT id) AS unqiueUserCnt FROM users
GROUP BY substr(created_at,1,7) ORDER BY yearMonth DESC;
-- 원래는 SQL문 실행 순서가 from -> where -> group by -> having -> select -> order by이기 때문에,
-- SELECT 구문에서 만든 ALIAS는 group by 절에 사용할 수 없다. 오라클 DB와 SQL 서버에서는 안된다.
-- 그러나 MySQL이나 PostgreSQL, MariaDB와 같은 몇몇 DBMS의 경우에는 이를 허용하도록 컴파일하는 것으로 보인다.
-- DB간 호환성을 염두에 두고 있을때는 alias를 사용하지 않는 것이 안전하다.

-- 어제 이어서 JOIN 관련 필기

-- 여러 테이블을 하나의 FORM에서 다룰 때에는 별칭을 사용가능 (Alias와는 다름.) 밑의 쿼리문과 같이.
-- alias와 달리 order by에서 사용할 수 있다.
-- ON 두 테이블의 공통된 부분을 지정하여 연결하는 연산자.
-- FROM에서 JOIN이 정렬된 후에 단일 테이블에 명령을 내리는 것처럼 쿼리를 작성할 수 있다.

/*
RDBMS에서는 복수의 테이블이 하나로 결합되기 위해서 두 테이블간의 공동된 부분인 Key라고 하며,
나중에 테이블에 반드시 1개 이상 존재하도록 설계해야한다.
테이블에서 개별 행을 유일하게 구분지어야 하기 때문에, 중복될 수 없고, null 값을 가질 수 없다.
(nullable=false로 Entity 클래스에서 지정했던 것.)
참고 - 한 테이블만 대상으로 할때는 key값이 중복될 리가 없지만, JOIN 연산을 수행하면 가능하다.
users와 orders 테이블을 join했을 때, 한 유저가 주문을 3번하면 id가 3개 있을 것이기 때문이다.
한번이라도 주문한 회원수를 중복 없이 구하려면 중복 제거를 하고 count할 필요가 있다.
 */

/*
1. 기본 키 (Primary Key) - 하나의 테이블에서 가지는 고유한 값
2. 외래 키 (Foreign Key) - 다른 테이블에서 가지는 기존 테이블의 값.
	- 외래 키는 다른 테이블의 고유한 키 값인 기본 키를 참조한다. orders의 user_id가 FK에 해당하는데, users에서는 pk에 해당한다.
	- 아까 참고에서의 예시와 같이 FK는 같은 값이 여러개 존재할 수 있다. 그래서 order by해도 똑같은 값이 여러개가 나온다.
 */

SELECT * FROM users u INNER JOIN orders o ON u.id=o.user_id ORDER BY u.id;
-- NULL 값인거는 안뽑음.
SELECT * FROM users u LEFT OUTER JOIN orders o ON u.id=o.user_id ORDER BY u.id;

SELECT * FROM users u, orders o WHERE u.id=o.user_id ORDER BY u.id;

/* JOIN의 종류
1. INNER JOIN - 키 값이 일치하는 행의 정보만 가지고 온다. -> 교집합에 해당
ex) users와 orders를 합하여 출력하나, 주문 정보가 있는 회원의 정보만 출력한다.
2. LEFT (OUTER) JOIN - 하나로 결합해 출력하는데 주문 정보가 없는 회웡의 정보도 출력한다. -> 교집합 + 왼쪽 차집합
 - 한쪽 데이터의 값을 보존해야 할 때 left join을 많이 사용한다.
 - 회원정보와 글, 댓글에 대한 데이터를 합칠 때, 글 안쓰고 눈팅만 한 사람의 데이터가 사라지면 안되니까.
3. RIGHT JOIN
4. FULL JOIN / FULL OUTER JOIN
ON 할때 가장 흔한 방식 PK=FK
 */
/*
결합 후에 컬럼 값에 접근할떄는 . 을 사용하는데, 이를 활용해서 SELECT 절에서도 * 대신에 표시할 컬럼을 정할 수 있따.
 */
SELECT u.id AS "u.id" ,u.username,u.country,o.id AS "o.id" ,o.user_id,o.order_date
FROM users u LEFT OUTER JOIN orders o ON u.id=o.user_id ORDER BY u.id;

-- 주문 정보가 없는 회원정보만 출력하기
SELECT u.id AS "u.id" ,u.username,u.country,o.id AS "o.id" ,o.user_id,o.order_date
FROM users u LEFT OUTER JOIN orders o ON u.id=o.user_id WHERE o.id IS NULL ORDER BY u.id;

-- users와 orders를 하나로 결합하고, 추가로 orderdetails에 있는 데이터도 출력해보자.
-- 주문 정보가 없는 회원의 주문 정보도 모두 출력하고
-- u.id,u.username,u.phone,o.user_id,o.id_od.order_id,od.product_id
SELECT u.id AS "u.id" ,u.username,u.phone,o.user_id,o.id AS "o.id" ,od.order_id,od.product_id
FROM users u LEFT JOIN orders o ON u.id=o.user_id
LEFT JOIN orderdetails od ON o.id=od.order_id ORDER BY u.id;
-- JOIN 연산을 여러번 수행하여 순차적으로 결합할 수 있다.

-- users와 orders를 하나로 결합하여 출력해보자.
SELECT *
FROM users u RIGHT JOIN orders o ON u.id=o.user_id;
-- RIGHT JOIN - LEFT JOIN과 기능은 동일하나,
-- LEFT JOIN과 달리 합쳤을 때를 기준으로 u.id는 없지만 o.user_id가 있는 경우도 출력된다.
-- 다만, 여기서는 회원가입하지 않은 유저는 주문할 수 없기 때문에 inner join과 동일한 결과가 나온다.
-- 이런 이유로 실무에서는 LEFT JOIN을 권장한다.

-- CROSS JOIN - 가능한 모든 조합을 만들어낼 때 사용한다. ON을 사용할 수 없다. (Cartesian Product)
-- 많은 연산을 요구하고, null값도 많이 나와 실제론 거의 사용하지 않는다.
SELECT * FROM users u CROSS JOIN orders o ORDER BY u.id;

-- 연습 문제
-- 1. users와 staff를 참고하여 회원 중 직원인 사람의 회원 id, 이메일, 거주 도시, 거주 국가, 성, 이름을 한번에 출력
SELECT u.id, u.username, u.city, u.country, s.first_name, s.last_name FROM users u INNER JOIN staff s ON u.id=s.user_id;
-- 2. staff와 orders를 참고하여 직원 아이디가 3번, 5번인 직원의 담당 주문을 출력하기.
-- (직원 아이디, 성, 주문 아이디, 주문 일자 출력하기)
SELECT s.id, s.first_name, o.id, o.order_date
FROM staff s INNER JOIN orders o ON s.user_id = o.user_id
WHERE s.id=3 OR s.id=5;
-- 3. users와 orders를 참고하여 회원 국가별 주문 건수를 내림차순으로 출력하기.
SELECT count(DISTINCT o.id) AS total, u.country FROM users u
INNER JOIN orders o ON u.id=o.user_id GROUP BY u.country ORDER BY total desc;
-- 4. orders와 orderdetails, products를 참고하여 회원 아이디별 주문 금액의 총합을 정상 가격과 할인 가격 기준으로 각각 구하기.
-- (정상 가격 주문 금액의 총합 기준으로 내림차순으로 정렬하기.)
SELECT o.user_id, sum(p.price*quantity), sum(p.discount_price*quantity) FROM orders o
INNER JOIN orderdetails od ON o.id=od.order_id INNER join products p ON od.product_id = p.id
GROUP BY o.user_id ORDER BY sum(p.price*quantity) DESC;

-- FULL OUTER JOIN의 경우 ON 조건에 부합할때만 결과를 도출하지만, CROSS JOIN은 모든 경우의 수를 출력하기 때문에 ON 조건을 명시하는 일이 거의 없다.


