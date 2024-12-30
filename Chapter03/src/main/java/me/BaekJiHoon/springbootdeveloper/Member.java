package me.BaekJiHoon.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//객체가 직접 저장되는 중요한 것.

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Member {
    @Id //후보키 같은 거 같은데...
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false) //테이블 상의 컬럼 명이 id
    private Long id;

    @Column(name = "name",nullable = false) //테이블 상의 컬럼 명이 name //member라는 이름의 테이블에 접근하는데 사용하는 객체
    private String name;

    //추후 member 테이블과 Member 클래스를 매핑하는 코드를 작성할 에정.
}

//여기는 데이터 매핑하는 곳인가?