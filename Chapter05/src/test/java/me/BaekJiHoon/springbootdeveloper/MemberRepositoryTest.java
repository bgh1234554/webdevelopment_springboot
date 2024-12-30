package me.BaekJiHoon.springbootdeveloper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/*
    MemberRepositoryTest를 위한 데이터 조회를 위해서 입력된 데이터가 필요하기 때문에 테스트용 데이터를 추가할 예정.
    test -> resources에 interst-members.sql 추가 예정.

    applciation.yml 의미 -> src/main/recources 폴더 내에 있는 data.sql파일을 자동으로 실행하지 못하게 하는 코드.
 */
@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    // /는 폴더 하나 올라가서 라는 의미. 안쓰면 현재 폴더 내를 의미.
    //멤버 전체를 찾고, 리스트의 크기가 3인지 찾는 테스트 메서드.
    @Sql("/insert-members.sql")
    @Test
    public void getAllMembers(){
        List<Member> members = memberRepository.findAll();
        //인터페이스에서 상속받은거라 메서드명이 정해져있음.

        assertThat(members.size()).isEqualTo(3);
    }
    /*
    id가 2인 멤버를 찾는 메서드
    SELECT *(all, 모든 컬럼을 조회) FROM member where id=2;
     */
    @Sql("/insert-members.sql")
    @Test
    public void getMemberById(){
        Member member = memberRepository.findById(2L).get(); //2L은 long으로 2.
        assertThat(member.getId()).isEqualTo(2);
        assertThat(member.getName()).isEqualTo("B"); //따옴표 상관 없음.
    }
    /*
    id가 아니라 name을 기준으로 찾아보기.
    id는 기본키인데, name의 경우에는 기본키가 아니라, findByName과 같은 메서드가 존재하지 않는다.
    필수 컬럼이 아닌 것을 조건으로 검색하기 위해선, MemberRepository에서 추상 메서드를 추가할 필요가 있음.
    -> findByName 추가함.
     */
    @Sql("/insert-members.sql")
    @Test
    public void getMemberByName(){
        Member member = memberRepository.findByName("C").get();
        assertThat(member.getId()).isEqualTo(3);
    }
    /*
    MemberRepository.java에 method를 정의하는 것을 '쿼리 메서드'라고 한다.
    일반적인 경우 JPA가 정해준 메서드 이름 규칙(FindBy....)을 따르면
    쿼리문을 특별히 작성하지 않아도 메서드처럼 사용할 수 있다.
    원래라면 SELECT * FROM member where name='C'; 라고 해야하나, 자바만으로 구현할 수 있다.
    
    여기까지는 조회 관련 메서드
    전체 조회 -> FindAll() ID 조회 -> FindByID()
    특정 컬럼으로 조회 -> 쿼리 메서드 규칙에 맞게 정의 후 -> FindBy...()
    이후 .get() 하면 객체가 나옴.
    
    추가/삭제 메서드
    INSERT INTO member (id,name) VALUES (1,'A'); 라는 쿼리가 있다고 할 때
    JPA 에선 save()라는 메서드를 사용한다.
    여러 엔티티를 한번에 저장할 때에는 saveAll() 메서드를 사용한다.
     */
    @Test //이 메서드에서 1,'A'에 해당하는 객체를 저장할 것이기 때문에 @Sql Annotation이 필요 없음.
    public void saveMember(){
        Member member = new Member(1L,"A");
        memberRepository.save(member);
        //assertThat의 괄호가 어디까지 이어져 있는지 잘 보기.
        assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("A");
    }
    @Test
    public void saveMembers(){
        //넣고 싶은 요소를 of메서드를 이용하면 생성자만 쓰면서 바로바로 넣을 수 있다.
        List<Member> members = List.of(new Member(2L,"baek"),new Member(3L,"kim"), new Member(4L,"kim")
                ,new Member(12L,"baek2"),new Member(13L,"baek2"));
        memberRepository.saveAll(members);
        assertThat(memberRepository.findAll().size()).isEqualTo(5);
    }
    /*
    DELETE FORM member where id=2;
    JPA에서는 deleteById() 메서드로 삭제 가능.
     */
    @Sql("/insert-members.sql")
    @Test
    public void deleteMemberById(){
        memberRepository.deleteById(2L); //없으니가 .get()을 할 필요가 없다.
        assertThat(memberRepository.findById(2L).isEmpty()).isTrue();
    }
}