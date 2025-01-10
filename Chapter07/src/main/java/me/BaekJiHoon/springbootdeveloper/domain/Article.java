package me.BaekJiHoon.springbootdeveloper.domain;
/*
    domain 패키지 -> Article.java 파일
 */

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id //id 필드를 기본키 (PK) 로 삼는다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //사용하는 DB 의 구조에 따라서 ID 자동 생성 방식을 맡긴다.
    @Column(name="id",updatable = false) //이름은 id, 수정 불가능하게 updatable=false
    private Long id; //Long 자료형은 DB 로 넘어갔을 때, BIGINT 로 바뀐다.

    @Column (name="title",nullable=false) // "title"이라는 not null 칼럼과 매핑
    private String title;//컬럼 하나 더 정하기

    @Column (name="content",nullable = false)
    private String content;

    @Builder //Builder 패턴으로 객체 생성. 명시적으로 어떤 필드에 어떤 값이 들어가는지 볼 수 있어서 많이 사용한다.
    public Article(String title, String content){
        this.title=title;
        this.content=content;
    }
    /*
        @Builder Annotation 은 롬복에서 지원하는 Annotation 으로, 생성자 위에 입력하면 빌더 패턴 방식으로 객체 생성 가능.
        객체를 유연하고 직관적으로 생성할 수 있기 때문에, 개발자들이 애용하는 디자인 패턴이다.
        어느 필드에 어떤 값이 들어가는지 명시적으로 파악할 수 있다.
     */
    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }
    // 이상의 update 메서드 정의 후에 -> 수정 요청과 관련된 DTO를 작성한다.
}
