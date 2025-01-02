SELECT * FROM users;
-- where절 : 조건에 합치하는 것을 고른다.
/* 이전까지는 테이블 전체거나 혹은 특ㅈ어 컬럼에 관련된 부분을 조회했지만,
 * 현업에서는 데이터의 일부 컬럼을 가져오거나 상위 n개의 데이터를 가져오는 것 뿐만 아니라,
 * 특정 컬럼의 값이 A인 데이터만 가져오는 등 복잡한 쿼리를 작성할 일이 많다.
 */

SELECT * -- 보통 이렇게 한줄 들여쓰는 경우가 많다.
	FROM users
	WHERE country != "Korea" AND country != "USA";

-- SELECT * FROM users WHERE country = "Korea" AND id = 10;
-- WHERE 절은 여러 조건을 동시에 적용할 수 있고, 조건의 개수에 제한은 없다.
-- AND/OR/BETWEEN 논리 연산자를 사용할 수 있다.

SELECT * FROM users WHERE created_at BETWEEN "2010-12-01" AND "2011-01-01";

/* 컬럼명이 먼저 나오고 =,!=,BETWEEN 적용
between은 시작값과 종료값을 포함하는 범위 내의 데이터를 조회한다.*/
SELECT * FROM users WHERE created_at >= "2010-12-01" AND created_at <= "2011-01-01";

SELECT * -- 보통 이렇게 한줄 들여쓰는 경우가 많다.
	FROM users
	WHERE country = "Korea" OR country = "USA" OR country = "UK";

SELECT * -- 보통 이렇게 한줄 들여쓰는 경우가 많다.
	FROM users
	WHERE country != "Korea" AND country != "USA" AND country != "UK";

SELECT * FROM users WHERE country NOT IN ("Korea","USA","UK");
SELECT * FROM users WHERE country IN ("Korea","USA","UK");

-- LIKE: 해당 전치사 뒤의 작은따옴표 큰따옴표 내에서는 와일드카드를 사용할 수 있다.
-- SQL을 해석하는 컴퓨터가 LIKE 코드를 읽는 순간 와일드카드를 감지한다. "%", "_"
-- 퍼센트는 글자수 제한 없고, _는 글자수까지 생각한다.

SELECT * FROM users WHERE country NOT LIKE "S%";

-- IS NULL / IS NOT NULL : null일때는 = 사용 못하니까.
SELECT * FROM users WHERE created_at IS NULL;

-- 연습 문제
SELECT created_at, phone, city, country FROM users WHERE country="Mexico";
SELECT *, (price-discount_price) AS discount_amount FROM products WHERE id<=20 AND price>=30
	ORDER BY discount_amount DESC, price desc;
SELECT * FROM users WHERE country NOT IN ("Korea","Canada","Belgium");
SELECT id, name, price FROM products WHERE name LIKE "N%";
SELECT * FROM orders WHERE order_date NOT BETWEEN "2015-07-01" AND "2015-10-31";

-- ORDER BY - 정렬. ASC 오름차순, DESC 내림차순
-- substring(string,index,len) SQL은 index가 1부터 시작하네
SELECT *,substring(phone,5,4) AS substr FROM users ORDER BY id;

SELECT * FROM users ORDER BY city DESC;
-- orderby 할때 컬럼명 안 적고 컬럼의 순서대로 숫자를 적을 수 있다.
SELECT * FROM users ORDER BY 1 DESC;

SELECT username,phone,city,country,id FROM users ORDER BY 1;
-- 이렇게 하면 username 순으로 정렬된다. 순서는 ASCII 코드 기준. 데이터 추출이 끝난 후를 기점으로 적용된다.
SELECT city,id FROM users ORDER BY city DESC, id;

-- 연습 문제
SELECT * FROM products ORDER BY price DESC;
SELECT * FROM orders ORDER BY order_date DESC;
SELECT * FROM orderdetails ORDER BY product_id DESC, quantity;

/*
 * 1. 배달 서비스 예시
 * 	1) 2023-08-01에 주문한 내역중 쿠폰 할인이 적용된 내역 추출
 * 	- SELECT * FROM 주문정보 WHERE 주문일자="2023-08-01" AND 쿠폰할인금액>0;
 * 	2) 마포구에서 1인분 배달이 가능한 배달 음식점 수
 * 	- SELECT * FROM 음식점정보 WHERE 지역 ="마포구" AND 1인분가능여부="1";
 */



