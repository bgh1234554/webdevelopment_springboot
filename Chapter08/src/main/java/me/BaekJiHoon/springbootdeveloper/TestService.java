package me.BaekJiHoon.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    MemberRepository memberRepository;  // 1. 빈 주입

    public List<Member> getAllMembers() {
        return memberRepository.findAll();  // 2. 멤버 목록 얻기  // 왜 findAll()인가에 주목하셔야합니다.
    }
    /*
        1. MemberRepository라는 빈을 주입 받은 후에,
        2. findAll() 메서드를 호출해서 멤버 테이블에 저장된 멤버 목록을 가져옵니다.
            -> 개발자가 직접적으로 정의한 메서드가 아니라 JpaRepository라는 springboot 관련 클래스에서
            메서드를 상속 받아서 사용하는 경우
     */
}

/*
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
HTTP 요청 ---> TestController <-----> TestService <-----> MemberRepository <-----> Database
url:/test ---> 프레젠테이션 계층 <----> 비즈니스 계층 <----> 퍼시스턴스 계층 <---> 데이터베이스
 */