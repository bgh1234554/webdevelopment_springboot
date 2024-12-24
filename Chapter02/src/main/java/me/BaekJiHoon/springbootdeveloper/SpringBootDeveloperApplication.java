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
    1. Spring vs Spring Boot
    
        Enterprise Application: 대규모로 복잡한 데이터를 관리하는 어플리케이션
        많은 사용자의 요청을 동시에 처리해야하므로 서버 성능과 안정성, 보안 이슈 등
        모든 것을 신경쓰면서 사이트 기능, 비즈니스 로직을 개발하기가 매우 어려워서 등장하게 된 것이 Spring
        
        Spring Boot
            스프링은 장점이 많지만, 설정이 복잡하다는 단점이 있다. 단점을 보완하기 위해 나온 제품이 스프링 부트.
            
            특징
            1) Tomcat, 제티, 언더토우와 같은 웹 어플리케이션 서버(WAS)가 내장되어있어 따로 설치하지 않아도 독립적으로 사용 가능.
            2) 빌드 구성을 단순화하는 스프링 부트 스타터를 제공
            3) XML 설정을 하지 않고 자바 코드로 작성 가능
            4) JAR을 이용하기 때문에 자바 옵션만으로 배포가 가능
            5) 애플리케이션 모니터링 및 관리 도구인 Spring Actuator 제공

       결국 Spring Boot는 Spring에 속해있는 도구 중 하나임.

       차이점 :
       1) 구성의 차이 -> 스프링은 앱 개발에 필요한 환경을 수동으로 구성하고 정의하지만,
       스프링부는 스프링 코어와 스프링 MVC의 모든 기능을 자동으로 로드하기 때문에, 수동으로 개발환경을 구성할 필요가 없음.
       2) 내장 WAS 유무 -> 스프링 애플리케이션은 일반적으로 톰캣과 같은 WAS에서 배포.
       하지만 스프링부트는 자체적인 WAS를 가지고 있어 JAR 파일만 만들면 별도로 WAS 설정을 하지 않아도 애플리케이션을 실행할 수 있음.

       2. 스프링 컨셉
        1) 제어의 역전
            Inversion of Control: 다른 객체를 직접 생성하거나 제어하는 것이 아니라 외부에서 관리하는 객체를 가져와 사용하는 것.
            ex) 클래스 A에서 클래스 B의 객체를 생성한다고 하자.
            public class A {
                B b = new B();
            }
            이와 다르게 B 객체를 직접 생성하는 것이 아니라, 어딘가에서 얻어와 사용하는 것이다.
            실제로 스프링은 스프링 컨테이너에서 객체를 관리, 제공하는 역할을 한다.
            ex) 스프링 컨테이너가 객체를 관리하는 방식의 자바 코드
            public class A {
                private B b;
            }
        2) 의존성 주입
            Dependency Injection : 어떤 클래스가 다른 클래스에 의존한다는 의미

            @Autowired Annotation이 중요. -> 스프링 컨테이너에 존재하는 Bean을 주입하는 역할.
            Bean: 스프링 컨테이너가 관리하는 객체
            public class A {
                @Autowired
                B b;
            }
        3) 빈과 스프링 컨테이너
            3-1) 스프링 컨테이너: 빈이 생성되고 소멸되기까지의 생명주기를 관리함.
            또한 @AutoWired와 같은 Annotation을 사용해 빈을 주입받을 수 있게 DI를 지원함.
            3-2) 빈: 스프링 컨테이너가 관리하는 객체 -> 위의 코드에서 B가 빈에 해당하는 것.
            스프링은 빈을 스프링 컨테이너에 등록하기 위해 XML 파일 설정, Annotation 추가 등의 방법을 제공함.
            (빈을 등록하는 방법은 여러가지지만, 수업 중에선 Annotation을 사용함)
            @Component
            public class MyBean{} 과 같이 @Component Annotation을 붙이면 MyBean 클래스가 빈으로 등록됨.
            이후 스프링 컨테이너에서 이 클래스를 관리하게 되고, 빈의 이름은 첫 문자를 소문자로 바꾸어서 관리함.
            클래스 MyBean의 빈의 이름은 myBean.
        4) 관점 지향 프로그래밍 (Aspect Oriented Programming)
        - 프로그래밍에 대한 관점을 핵심 관점 / 부가 관점 둘로 나누어서 모듈화하는 것.
            ex) 계좌이체, 고객관리를 하는 프로그래밍이 있다고 했을 때, 각 프로그래밍에는
            지금까지 일어난 일을 기록하는 로깅 로직과, 여러 데이터를 관리하기 위한 DB 연결 로직이 있을텐데, 이 때
            계좌이체와 고객관리가 핵심 관점이고 로깅과 DB 연결 로직이 부가 관점임.
        5) 이식 가능한 서비스 추상화
        Portable Service Abstraction: 스프링에서 제공하는 다양한 기술들을 추상화해 개발자가 쉽게 사용하는 인터페이스
            ex) 클라이언트의 매핑 / 클래스, 메서드의 매핑을 위한 Annotation들
            스프링에서 DB에 접근하기 위한 기술로 JPA, MyBatis, JDBC등 어떤 기술을 사용하든 일관된 방식으로 DB에 접근하도록
            인터페이스를 지원한다. WAS역시 PSA의 예시 중 하나로,
            어떤 서버(TomCat, 언더토우, 네티 등)를 사용하더라도 동일한 코드를 사용 가능하다.
            (메서드 이름이 통일되어 있어서 다른 곳으로 코드를 옮겨도 작동이 가능함.)
       3. 스프링부트 3 둘러보기
       springbootdeveloper폴더에 TestController 클래스 만들기

       스프링 부트 스타터
            스프링부트 스타터는 의존성이 모요있는 그룹에 해당함.
            스타터를 사용할 경우 필요한 기능을 간편하게 설정 가능
            spring-boot-starter-{작업유형}
            spring-boot-starter-web : Spring MVC를 사용해 RESTful 웹 서비스를 개발할때 필요한 의존성
            spring-boot-starter-test : Spring 어플리케이션을 테스트하기 위해 필요한 의존성 모음
            spring=boot-starter-validation : 유효성 검사를 위해 필요한 의존성 모음
            spring-boot-starter-acutator : 모니터링을 위해 어플리케이션에서 제공하는 다양한 정보를 제공하기 쉽게 하는 의존성 모음
            spring-boot-starter-jpa: ORM을 사용하기 위한 인터페이스의 모음인 JPA를 더 쉽게 사용하기 위한 의존성 모음

       스프링 부트 3는 자바 버전 17부터 사용가능하다.
 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class,args);
    }
}
/*
    처음 SpringBootDeveloperApplication 파일을 실행시키면 whiteLabel error page가 뜬다.
    현재 요청에 해당하는 페이지가 존재하지 않기 때문에 생겨난 문제.
    -> 하지만 스프링 애플리케이션은 실행됨.

    그래서 error 페이지가 기분 나쁘니까 기본적으로 실행될 때의 default 페이지를 하나 생성함.

    20241223 MON.
    1. IDE 설치 (IntelliJ Community)
    2. Git 설치
    3. 깃허브 연동 -> web_development_springboot
    4. IntelliJ에 Gradle 및 SpringBoot 프로젝트 생성
    5. POSTMAN 설치
 */