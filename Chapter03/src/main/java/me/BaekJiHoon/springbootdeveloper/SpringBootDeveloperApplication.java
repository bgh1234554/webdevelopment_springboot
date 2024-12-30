package me.BaekJiHoon.springbootdeveloper;
/*
    New Project 생성시 :
        1. build system -> Gradle
        2. DLS -> Groovy
        3. name = ArtifactID
        4. build.gradle 설정 복사 후 싱크 버튼을 눌러야함.
        5. resources 내에 static 내에 index.html이라고 하는데, 해당 폴더명의 경우 대부분의 개발자들이 합의한 형태.
        6. 앞으로의 수업 시간에는 new project 생성시에 자세히 풀이할 일 없으니 알아서 해야함.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
    1. 스프링부트 3 구조
        각 계층이 양 옆의 계층과 통신하는 구조
        계층 : 각자의 역할과 책임이 있는 어떤 소프트웨어의 구성 요소
        -> 각 계층은 소통할 수는 있지만 다른 계층에 직접 간섭하거나 영향을 미치진 않음.
        (특정 클래스가 다른 클래스의 값을 함부로 바꿔서는 안됨.

        스프링부트에서의 계층
            1) Presentation
                HTTP 요청을 받고 이 요청을 비즈니스 계층으로 전송하는 역할 -> Controller가 프레젠테이션 계층 역할
                (TestController 클래스와 같은 것)
            2) Business
                모든 비즈니스 로직을 처리
                    * 비즈니스 로직 - 서비스를 만들기 위한 로직. 웹 사이트에서 벌어지는 모든 작업
                서비스가 비즈니스 계층의 역할을 함.
            3) Persisetence
                모든 DB 관련 로직을 처리. 이 과정에서 DB에 접근하는 DAO 객체를 사용할 수도 있다.
                -> Repository가 Persistence 계층 역할을 함.

            계층은 개념의 영역이고 Controller, Service, Repository를 통해 구현됨.

        스프링부트 프로젝트 디렉토리 구성 - 정해진 구조는 없지만 추천 프로젝트 구조가 있고 많은 개발자들이 이를 따름.
        main - 실제 코드를 작성하는 공간. 프로젝트 실행에 필요한 소스 코드나 리소스 파일은 여기 다 들어있음.
        test - 프로젝트의 소스 코드를 테스트할 목적의 코드나 리소스 파일이 들어 있음.
        build.gradle -  빌드를 설정하는 파일. 의존성이나 플러그인 설정과 같이 빌드에 필요한 설정을 할 때 사용.
        settings.gradle - 빌드할 프로젝트의 정보를 설정하는 파일.

        main 디렉토리 구성하기 (java와 resources로 구성되어 있음. 아직 추가하지 못했던 프로젝트 내의 구성 요소를 추가.)
        1단계 - HTML과 같은 뷰 파관련 파일을 넣을 templates 디렉토리를 resources 아래에 생성
        2단계 - JS, CSS, 이미지와 같은 정적 파일을 넣는 용도로 사용할 static 디렉토리 생성
        3단계 - 스프링 부트 설정을 할 수 있는 application.yml 파일을 생성
                -> 스프링 부트 서버가 실행되면 자동으로 로딩되는 파일로, DB의 설정 정보, 로깅 설정 정보 등이 들어갈 수도 있고,
                직접 정의할 때 사용하기도 함.

    2. 스프링 부트 3 프로젝트 발전시키기
        각 계층의 코드를 추가할 예정 -> 계층이 무엇이고, 계층을 어떻게 잡아나가는지에 대한 감을 잡아나갈 수 있음.
        지금은 의존성 추가 후 -> 프레젠테이션 게층, 비지니스 계층, 퍼시스턴스 계층 순서로 코드를 추가할 에정.

        implementation : 프로젝트 코드가 컴파일 시점과 런타임에 모두 해당 라이브러리를 필요로 함.
        testImplementation : 프로젝트의 테스트 모드를 컴파일하고 실행할 때만 필요함.
        runtimeOnly : 런타임에만 필요한 의존성을 지정, 컴파일 시에는 필요 없고 어플리케이션을 실행할 때 필요로 함.
        compileOnly : 컴파일 시에만 필요한 의존성.
        annotationProcessor : 컴파일 시에 Annotation을 처리할 때 사용하는 도구의 의존성 지정.

        프레젠테이션, 서비스, 퍼시스턴스 계층 만들기
            1. 프레젠테이션 계층에 속하는 컨트롤러 관련 코드 작성 -> TestController
            2. 비즈니스 계층 코드 -> TestService.java
            3. 퍼시스턴스 계층 코드 -> Member.java -> 실제 DB에 접근하는 코드 (Model인것 같다)
            4. 매핑 작업에는 인터페이스 파일이 필요. MemberRepository 인터페이스를 같은 위치에 생성.

       작동 확인하기
            1단계 -> Resources 폴더에 SQL문 추가
                resources -> new -> file -> data.sql
            2단계 -> application.yml 파일 수정
            3단계 -> 서버 실행 후 Ctrl+F눌러서 Table 생성됐는지 확인
            4단계 -> Postman에서 HTTP 요청을 시도해본다.
                1) 포스트맨 실행
                2) HTTP 메서드를 GET으로 설정하고 URL에 https://localhost:8080로 설정
                3) SEND 버튼 누르기
                4) 200 OK인지 확인하기
                
HTTP 요청 ---> TestController <-----> TestService <-----> MemberRepository <-----> Database
url:/test ---> 프레젠테이션 계층 <----> 비즈니스 계층 <----> 퍼시스턴스 계층 <---> 데이터베이스
 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class,args);
    }
}