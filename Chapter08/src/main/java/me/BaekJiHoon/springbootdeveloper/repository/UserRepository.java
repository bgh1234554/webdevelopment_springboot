package me.BaekJiHoon.springbootdeveloper.repository;

import me.BaekJiHoon.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
/*
    이메일로 사용자를 식별, 즉 email을 username으로 봐도 됨.
    따라서 사용자 정보를 가져오기 위해서는 스프링 시큐리티가 이메일을 전달받아야 한다.
    스프링 데이터 JPA는 메서드 규칙에 맞춰서 선언하면 이름을 분석해 자동으로 쿼리(SQL문)을 생성해준다.

    findByEmail() 메서드는 실제 데이터베이스에 회원 정보를 요청할 때 다음 쿼리를 실행한다.

    findByEmail() 메서드가 요청하는 쿼리의 예시

        FROM users
        WHERE email = #{email}


    findByNAme() - "name" 컬럼의 값 중 파라미터로 들어오는 값과 같은 데이터 반환
        - WHERE name =?1
    findByNameAndAge() - 파라미터로 들어오는 값 중 첫 번째 값은 "name" 컬럼에서 조회하고, 두 번째 값은 "age" 컬럼에서 조회한 데이터에서 반환
        - WHERE name=?1 AND age =?2
    findByNameOrAge() - 파라미터로 들어오는 값 중 첫 번째 값이 "name" 컬럼에서 조회되거나 두 번째 값이 "age"에서 조회되는 데이터 반환
        - WHERE name=?1 OR age =?2
    findByAgeLessThan() - "age" 컬럼의 값 중 파라미터로 들어온 값보다 작은 데이터 반환
        - WHERE age <?1
    findByAgeGreaterThan() - "age" 컬럼의 값 중 파라미터로 들어온 값보다 큰 데이터 반환
        - WHERE age > ?1
    findByName(Is)Null() - "name" 컬럼의 값 중 null인 데이터 반환
        - WHERE name IS NULL
 */