package me.BaekJiHoon.springbootdeveloper;
/*
    모든 프로젝트에는 main에 해당하는 클래스가 존재하는데, 이 클래스가 main으로 사용될 예정.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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