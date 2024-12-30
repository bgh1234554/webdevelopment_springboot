package me.BaekJiHoon.springbootdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    //List<Member> findAll(); //JpaRepository라는 springboot 관련 클래스에서 메서드를 상속 받음.
}
