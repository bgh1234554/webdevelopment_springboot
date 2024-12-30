package me.BaekJiHoon.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
5장. 데이터베이스 조작이 편해지는 ORM
    1. 데이터베이스란?
        데이터를 효율적으로 보관하고 효율적으로 꺼내볼 수 있는 공간.

        DBMS(DataBase Management System): 기본적으로 DB는 많은 사람이 공유할 수 있어야 하고, 동시 접근이 가능해야 하는
        등 많은 요구사항이 존재한다. 이를 만족시키면서도 효율적으로 데이터베이스를 관리하는 체계가 DBMS.
        대부분 개발자들이 편하게 DB라고 이야기하는 MySQL, Oracle DB, DBeaver와 같은 것들은 실제로는 DBMS에 해당함.

        관계형 DBMS - RBDMS : 테이블 형태로 이루어진 데이터 저장소.


               회원 테이블
                1열           2열           3열
               +----------------------------------+
               | ID         | 이메일       | 나이  | - header / column
               ------------------------------------
               | 1          | a@test.com  | 10    | - 1행
               | 2          | b@test.com  | 20    | - 2행
               | 3          | c@test.com  | 30    | - 3행
               +----------------------------------+
                기본키(PK) :
                Prime Key
        Entity -> 테이블 Column/Header -> 데이터 이름 들어있는 곳.
        Primary Key - 기본키

        H2, MySQL
         - 여기서 사용할 RDMBS는 H2, MySQL
         H2 - 자바로 작성되어 있는 RDMBS : 스프링부트가 지원하는 인메모리 RDBMS
            데이터를 다른 공간에 따로 보관하는 것이 아니라 애플리케이션 자체 내부에 데이터를 저장하는 특징이 있다.
            앱 재실행시 데이터가 초기화됨. 실제로는 사용하지 않음.
         MySQL - 실제 서비스로 올릴 때 사용할 RDBMS

       DB 관련 용어
        1) 테이블 - DB에서 데이터를 구성하기 위한 가장 기본적인 단위. 행과 열로 구성되어있고,
        행은 여러 속성으로 구성되어 있다.
        2) 행(Row) - 레코드라고도 부르며, 가장 기본적인 구성 요소. 테이블의 가로로 배열된 데이터의 집합.
        반드시 고유의 식별자인 기본키를 가진다.
        3) 열(Column) - 행에 저장되는 유형의 데이터. Java에서는 class의 field라고 할 수 있다. 무결성을 보장함.
        이메일에 숫자가 들어가거나, 나이에 문자가 들어갈 수 없기 때문에 데이터에 대한 무결성을 보장함.
        4) 기본키(Primary Key) - 행을 구분할 수 있는 식별자. 테이블에서 유일해야 하며 "중복값을 가질 수 없다."
        보통 데이터를 수정하거나 삭제하고, 조회할때 사용되며 다른 테이블과 관계를 맺어 데이터를 가져올 수도 있다.
        또한, 기본키의 값은 수정되어서는 안되며 유효한 값이어야 하기 때문에, null이 될 수 없다
        (JAVA에서 nullable=false)
        5) 쿼리 (Query) - 데이터베이스에서 데이터를 조회하거나 삭제, 생성, 수정과 같은 처리를 하기 위해 사용하는 명령문.
        SQL (Structured Query Language) 이라는 데이터베이스 전용 언어를 사용하여 작성함.
    2. ORM이란?
        Object-Relational Mapping. 자바의 객체와 데이터베이스를 연결하는 프로그래밍 기법.
        ex) DB의 age, name 컬럼에 20, "홍길동"이라는 값이 있다고 가정했을 때, 이것을 자바에서 사용하려면 SQL을 이용하여
        데이터를 꺼내 사용하지만, ORm이 있다면 데이터베이스의 값을 마치 객체처럼 활용할 수 있다.
        -> SQL에 어려움을 겪더라도, 자바 언어로만 DB에 접근해서 원하는 데이터를 받아올 수 있다.
        -> 즉, 객체와 DB를 연결해 자바 언어로만 DB를 다룰 수 있도록 하는 도구.

        장점 :
            1) SQL을 직접 작성하지 않고, 사용하는 언어로 DB에 접근이 가능하다.
            2) 객체 지향적으로 코드를 작성할 수 있기 때문에 비지니스 로직에만 집중 가능.
            3) DB 시스템이 추상화되어 있기 때문에 MySQL에서 PostgreSQL로 전환을 하더라도 추가로 드는 작업이 거의 없음.
            4) 매핑하는 정보가 명확하기 때문에, ERD에 대한 의존도를 낮출 수 있고 유지보수가 편하다.
        단점 :
            1) 프로젝트의 복잡성이 커질수록 사용 난이도가 올라간ㄷ.
            2) 복잡하고 무거운 쿼리는 ORM으로 해결이 불가능한 경우가 있다. (JOIN이 여러개 or 서브쿼리가 복잡한 경우)

        스프링 데이터 JPA에서 제공하는 메서드 사용
            MemberRepository.java -> create test.
 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}