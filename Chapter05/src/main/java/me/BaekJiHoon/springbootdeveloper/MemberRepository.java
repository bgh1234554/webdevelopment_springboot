package me.BaekJiHoon.springbootdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //JpaRepository라는 springboot 관련 클래스에서 메서드를 상속 받음. 그래서 findAll을 사용하는 것.
}
