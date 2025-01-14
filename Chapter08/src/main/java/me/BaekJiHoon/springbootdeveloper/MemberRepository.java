package me.BaekJiHoon.springbootdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //JpaRepository라는 springboot 관련 클래스에서 메서드를 상속 받음.
    // 그래서 findAll을 사용하는 것. 이미 구현되어있는 메서드.

    //이름으로 멤버를 찾는 메서드 정의해보기.
    //이름은 검색 결과가 없을 수 있기 때문에 Optional 클래스를 가져온다.
    Optional<Member> findByName(String name);
}
