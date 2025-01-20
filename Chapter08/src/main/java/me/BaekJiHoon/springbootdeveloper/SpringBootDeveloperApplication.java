package me.BaekJiHoon.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //created_at과 updated_at을 자동 업데이트해주는 Annotation.
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
/*
    로그인/회원 가입 기능 구현하기
        회원 가입 구조와 로그인 구조는 유사함.

       /login 요청이 들어올 때,
       -> UserViewController가 해당 요청에 대한 분기 처리를 하고 WebSecurityConfig에 설정한 보안 관련 내용들을 실행
       -> UserDetailsService를 실행하면 요청을 성공하였을 때,
       -> defaultSuccessUrl로 설정한 /articles로 리다이렉트 하거나 csrf를 disable하거나 하는 등의 작업

       UserDetailsService에서는 loadUserByUsername() 메서드를 실행하여 이메일로 유저를 찾고 반환.

       유저는 User 클래스의 객체이고 UserRepository에서 실제 데이터를 가져올 예정.

       로그아웃
       /logout 요청이 들어오면
       -> UserApiController클래스에서 로그아웃 로직을 실행한다.
       로그아웃 로직 - SecurityContextLogoutHander에서 제공하는 logout() 메서드를 사용할 예정.

       1. 스프링 시큐리티
        1) 인증 vs 인가
            (1) 인증 (Authentication) : 사용자의 신원을 입증하는 과정
                사용자가 사이트에 로그인할 때 누구인지 확인하는 과정.
            (2) 인가 (Authorization) : 사이트에 특정 부분에 접근할 수 있는지 권한을 확인하는 작업
                관리자는 관리자 탭에 들어갈 수 있지만 일반 사용자는 관리자 페이지에 들어갈 수 없다.

            인증과 인가 관련 코드를 아무런 도구 없이 작성하려면 복잡하기 때문에 스프링 시큐리티를 사용한다.
        2) 스프링 시큐리티
         : 스프링 기반 애플리케이션의 보안을 담당하는 스프링 하위 프레임워크 보안 관련 옵션을 다수 제공해준다.
         CSRF, 세션 공격을 방어해주고, 요청 헤더도 보안 처리를 해주므로 보안 관련 개발을 할 때 부담을 덜어준다.

        3) 필터 기반으로 동작하는 스프링 시큐리티
            스프링 시큐리티의 풀 로그인 관정을 설정하는 건 그렇게 어렵지 않지만,
            내부적으로 보면 꽤 복잡한 과정을 거치게 된다.

            모두 외워야 하는 건 아니지만, 로그인 과정이 어떤 흐름으로 이어지는 지를 이해하면 더 잘 활용할 수 있다.

       2. 회원 도메인 만들기
            스프링 시큐리티를 활용한 인증, 인가 기능 구현 예정.
            회원 정보를 저장할 테이블을 만들고,
            테이블과 연결할 도메인을 만들고,
            이 테이블과 연결할 회원 엔티티를 만들 예정.
            회원 엔티티와 연결되어 데이터를 조회하게 해줄 리포지토리가 추가되고
            스프링 시큐리티에서 사용자 정보를 가져오는 서비스를 구현 예정.
 */