package me.BaekJiHoon.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter //get..를 사용할 수 있다.
@Entity
public class Member {
    @Id //Primary Key로 쓰겠다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)      // 테이블 상에서의 컬럼 명이 id라는 의미
    private Long id;    // DB 테이블의 'id' 컬럼과 매칭

    @Column(name = "name", nullable = false)   // 테이블 상에서의 컬럼 명이 name이라는 의미
    private String name;    // DB 테이블의 'name' 컬럼과 매칭
    /*
        각 애너테이션의 역할은 lombok의 경우 java 과정에서 다 설명했습니다.
        하지만 추후 다시 설명드리겠습니다.
        현재는 member라는 이름의 테이블에 접근하는 데 사용하는 객체로 이해하세요.
        이후 member 테이블과 Member 클래스를 매핑하는 코드를 작성할 예정입니다.
        위는 테이블을 구현하기 위한 클래스.
     */
    public void changeName(String name){
        this.name=name; //name의 필드값을 바꾸는 단순한 메서드. setName과 동일하나, 수정에 사용할 것이기 때문에
    } //메서드명을 직관적으로 지음. 생성시 SQL문을 통해서 생성했기 때문에 굳이 세터로 만들지 않았다.
    //만약 이 메서드가 @Transactional 애너테이션이 포함된 메서드에서 호출되면,
    //JPA는 변경감지기능을 통해 엔티티의 필드갑싱 변경될 때 해당 변경사항을 JPA에 자동 반영함.
}
