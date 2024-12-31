-- SQL에서 주석 다는 법. (실행 Ctrl+Enter, 주황색 화살표)
-- 1. SELECT - 보여달라/조회하라.
select "Hello, SQL";
select 12+7; -- SQL은 커서가 있는 문장만 컴파일된다. 연산도 가능하다.

-- 결과 창의 첫 행에 각각 Find, Insight, with SQL을 3개의 칸에 걸쳐 순서대로 출려하는데 First, Second, Third로 출력
select -- as로 컬럼 이름을 가독성을 위해 바꾼다.
	"FIND" as "First", "Insight" as 'Second', "with SQL" as Third;
-- 알아보기 쉽게 하기 위해서 하는 것. as는 사실 alias의 준말. 컬럼에 대한 별칭을 붙일때 사용하는 구문.
-- 큰 따옴표, 작은 따옴표, 따옴표 없음을 구분하지 않는다. 콤마는 나열할 때 사용한다.

select 35+172 as plus, 19*27 as times, "I love SQL" as "result";

select * from users natural join staff;

-- FROM : ~로부터. 한국어의 의미에서도 똑같음. FROM+테이블명.
select * from users where country = "Korea";
/*
 *는 all이라는 의미. 
 */
select * from products;
/* 왠지 모르겠지만 대문자로 적으면 소문자로 바뀐다. LIMIT : 개수 제한을 거는 명령어. (IDE에 따라 TOP인 경우도 있음.) */

select * from products limit 3; -- 상위 3개만 순서대로 가지고 온다.
select price, discount_price from products limit 10;

SELECT * FROM users LIMIT 7;

SELECT id, user_id, order_date FROM orders ORDER BY order_date DESC;
