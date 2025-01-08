package me.BaekJiHoon.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
6장
    1. 사전 지식: API와 REST API
        API: 프로그램 간에 상호 작용하기 위한 '''매개체'''
        ex) 손님이 식당에 들어가서 점워에게 요리 주문 -> 주방의 요리사보고 요리를 만들어달라고 요청
            손님 <--요청/응답--> 점원 <--요청/응답--> 주방
        여기서 손님은 Client, 주방은 Server에 해당함. 중간에 있는 점원이 API와 같은 역할.

        사용자: 웹사이트의 주소를 입력해 "구글 웹페이지를 보여줘"
        API: 요청을 받아서 서버에 전달
        서버: API가 준 요청을 처리해 결과물을 구성 후, 다시 API에 전달
        API: 최종 결과물을 브라우저에 보내줘서, 브라우저가 화면에 띄움.

        API - 클라이언트의 요청을 서버에 잘 전달하고, 서버의 결과물을 클라이언트에 잘 돌려주는 역할을 한다.

        REST API란?
        Representational State Transfer을 줄인 표현으로, 자원을 이름으로 구분해 자원의 상태를 주고받는 API 방식.

        URL의 설계 방식.

        특징
            REST API는 서버/클라이언트 구조, 무상태, 캐시 처리 기능, 계층화, 일관성과 같은 특징을 가지고 있다.
        장점:
            URL만 보고도 무슨 행동을 하는 API인지 명확하게 알 수 있다.
            무상태 특징으로 인해 클라이언트와 서버의 역할이 명확하게 분리된다.
            HTTP 표준을 사용하는 모든 플랫폼에서 사용할 수 있다. (이게 가장 큰 이유)
        단점:
            HTTP 메서드, 즉 GET, POST와 같은 방식의 개수에 제한이 있다.
            설계를 위해 공식적으로 제공되는 표준 규약이 없음. (RESTful 하게...라는 비공식적인 표준은 있음)

        장단점을 고려했을 때, 주소와 HTTP 메서드만 보고 요청의 내용을 파악할 수 있다는 장점으로,
        REST하게 디자인한 API를 보고 RESTful API라고 부르기도 한다.

        REST API를 사용하는 방법
            규칙 1. URL에는 동사를 쓰지 말고, 자원을 표지한다.
                * 자원 - 가져오는 데이터를 의미한다.
                ex) 학생 중 ID가 1인 학생의 정보를 가져온다고 할 때,
                /students/1 , /get-student?student_id=1과 같은 방식이 존재할 수 있는데,
                REST API에 맞는 형식은 전자에 해당한다.

                동사를 사용해서 생기게 되는 문제점 -> 데이터를 요청하는 URL을 설계할 때,
                누구는 get을, 누구는 show를 쓰면 get-student, show-data와 같이,
                합의가 이루어지지 않은 설계가 될 가능성이 있다.
                '기능/행위'에 해당하지만 RESTful API에서는 동사를 사용하지 않는다.
            규칙 2. 동사는 HTTP 메서드로
                HTTP 메서드란? 서버에 요청을 하는 방법을 나누는 것 -> POST, GET, PUT, DELETE
                    만들고, 읽고, 업데이트하고, 삭제한다. Create,Read,Update,Delete의 줄임말인 CRUD를 사용한다.
                ex) 블로그에 글을 쓰는 설계를 한다고 할 때,
                    1) id가 1인 블로그 글을 조회하는 API - GET/articles/1
                    2) 블로그에 글을 추가하는 API - POST/articles (아직 id가 없으니까 /1이 없다.)
                    3) 블로그의 글을 수정하는 API - PUT/articles/1
                    4) 블로그의 글을 삭제하는 API - DELETE/articles/1
                * GET/POST등은 URL에 입력하는 값이 아니라 내부적으로 처리하는 방식을 미리 정하는 것.
                실제로 HTTP 메서드는 내부에서 서로 다른 함수로 처리하는데, 대놓고 저렇게 적는 일은 없다.

                이 외에도 '/'는 계층 관계를 나타내는 데에 사용되거나, 밑줄 대신 하이픈을 사용하거나,
                자원의 종류가 Collection인지 Document인지에 따라 단수, 복수를 나누거나 하는 등의 규칙이 있지만,
                추후에 설명할 예정.

    2. 블로그 개발을 위한 엔티티 구성하기
    컬럼명 - 자료형 - nullable - Key - 설명
    id - BIGINT - N - 기본키 - 일련번호, 기본 키
    title - VARCHAR(255) - N - . -  게시물의 제목
    content - VARCHAR(255) - N - . -  내용
 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}