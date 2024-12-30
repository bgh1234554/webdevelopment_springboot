package me.BaekJiHoon.springbootdeveloper;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    MemberRepository memberRepository; //빈 주입
    public List<Member> getAllMembers() {
        return memberRepository.findAll(); //멤버 목록 얻기
    }
}
/*
    1. MemberRepository라는 빈을 주입받은 후에,
    2. findAll() 메서드를 호출해서 멤버 테이블에 저장된 멤버 목록을 가져옴.
    -> 개발자가 직접적으로 정의한 메서드가 아니라 JpaRepository라는 springboot 관련 클래스에서
    메서드를 상속 받아서 사용하는 경우.
 */

//여기서 요청 받은 일을 어떻게 수행할지 적고..